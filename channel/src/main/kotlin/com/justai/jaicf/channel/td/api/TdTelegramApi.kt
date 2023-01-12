package com.justai.jaicf.channel.td.api

import com.justai.jaicf.channel.td.TdTelegramClient
import it.tdlight.client.GenericUpdateHandler
import it.tdlight.client.TelegramError
import it.tdlight.jni.TdApi
import kotlinx.coroutines.runBlocking
import java.util.concurrent.TimeUnit
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TdTelegramApi(internal val client: TdTelegramClient) {
    private val sentMessages = mutableSetOf<TdSentMessage>()

    val me: TdApi.User by lazy { send(TdApi.GetMe()) }

    init {
        onUpdate<TdApi.UpdateMessageSendSucceeded> { event ->
            findSentMessage(event.oldMessageId)?.also {
                it.complete(event.message)
            }
        }
    }

    private fun addSentMessage(message: TdApi.Message) {
        synchronized(sentMessages) {
            sentMessages.removeAll { it.date < message.date - 10 }
            sentMessages.add(TdSentMessage(message))
        }
    }

    private fun findSentMessage(messageId: Long) = synchronized(sentMessages) {
        sentMessages.find { it.id == messageId }
    }

    internal fun isSentMessage(update: TdApi.Update) = when (update) {
        is TdApi.UpdateNewMessage -> findSentMessage(update.message.id) != null
        else -> false
    }

    inline fun <reified T : TdApi.Update> onUpdate(handler: GenericUpdateHandler<T>) =
        onUpdate(T::class.java, handler)

    fun <T : TdApi.Update> onUpdate(type: Class<T>, handler: GenericUpdateHandler<T>) =
        client.addUpdateHandler(type, handler)

    fun onUpdates(handler: GenericUpdateHandler<TdApi.Update>) =
        client.addUpdatesHandler(handler)

    fun onUpdateException(handler: (Throwable) -> Unit) =
        client.addUpdateExceptionHandler(handler)

    fun onException(handler: (Throwable) -> Unit) =
        client.addDefaultExceptionHandler(handler)

    fun awaitMessage(messageId: Long, timeoutInSeconds: Long = 5) =
        sentMessages.lastOrNull { it.id == messageId }?.get(timeoutInSeconds, TimeUnit.SECONDS)

    fun awaitLastMessage(timeoutInSeconds: Long = 5) =
        sentMessages.lastOrNull()?.get(timeoutInSeconds, TimeUnit.SECONDS)

    fun <R : TdApi.Object> send(function: TdApi.Function<R>) = runBlocking {
        suspendCoroutine<R> { continuation ->
            client.send(function) { res ->
                if (res.isError) {
                    continuation.resumeWithException(TelegramError(res.error))
                } else {
                    res.get().also { value ->
                        if (value is TdApi.Message) {
                            addSentMessage(value)
                        }
                        if (value is TdApi.Messages) {
                            value.messages.forEach(::addSentMessage)
                        }
                        continuation.resume(value)
                    }
                }
            }
        }
    }
}