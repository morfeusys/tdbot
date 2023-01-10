package com.justai.jaicf.channel.td.api

import it.tdlight.client.GenericUpdateHandler
import it.tdlight.client.SimpleTelegramClient
import it.tdlight.client.TelegramError
import it.tdlight.jni.TdApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TdTelegramApi(internal val client: SimpleTelegramClient) {
    private val handlerScope = CoroutineScope(newSingleThreadContext("TdTelegramApi Handler thread"))
    internal val sentMessages = mutableSetOf<TdApi.Message>()

    val me: TdApi.User by lazy { send(TdApi.GetMe()) }

    private fun <T : TdApi.Update> GenericUpdateHandler<T>.createAsync() = let { handler ->
        GenericUpdateHandler<T> { res ->
            handlerScope.launch { handler.onUpdate(res) }
        }
    }

    inline fun <reified T : TdApi.Update> onUpdate(handler: GenericUpdateHandler<T>) =
        onUpdate(T::class.java, handler)

    fun <T : TdApi.Update> onUpdate(type: Class<T>, handler: GenericUpdateHandler<T>) =
        client.addUpdateHandler(type, handler.createAsync())

    fun onUpdates(handler: GenericUpdateHandler<TdApi.Update>) =
        client.addUpdatesHandler(handler.createAsync())

    fun onUpdateException(handler: (Throwable) -> Unit) =
        client.addUpdateExceptionHandler(handler)

    fun onException(handler: (Throwable) -> Unit) =
        client.addDefaultExceptionHandler(handler)

    fun <R : TdApi.Object> send(function: TdApi.Function<R>) = runBlocking {
        suspendCoroutine<R> { continuation ->
            client.send(function) { res ->
                if (res.isError) {
                    continuation.resumeWithException(TelegramError(res.error))
                } else {
                    res.get().also { value ->
                        if (value is TdApi.Message) {
                            sentMessages.removeAll { it.date < value.date - 10 }
                            sentMessages.add(value)
                        }
                        continuation.resume(value)
                    }
                }
            }
        }
    }
}