package com.tdbot.api

import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.client.forwardMessages
import com.justai.jaicf.channel.td.client.getInlineQueryResults
import com.justai.jaicf.channel.td.client.sendMessage
import it.tdlight.client.GenericResultHandler
import it.tdlight.client.Result
import it.tdlight.client.SimpleTelegramClient
import it.tdlight.jni.TdApi

val isBotChat: (bot: BotClient) -> OnlyIf = { isChat { it.botUserId } }
val isNotBotChat: (bot: BotClient) -> OnlyIf = { isNotChat { it.botUserId } }

class BotClient {
    var botUserId: Long = 0
        private set

    private var api: SimpleTelegramClient? = null
    private val handlers = mutableMapOf<Int, HandlerHolder>()

    private fun removeOutdatedHandlers(timeout: Long = 180000) = synchronized(handlers) {
        handlers
            .filterValues { it.created < System.currentTimeMillis() - timeout }
            .keys.forEach(handlers::remove)
    }

    private fun onMessageReceived(message: TdApi.Message) {
        removeOutdatedHandlers()
        if (message.replyToMessageId != 0L) {
            api?.send(TdApi.GetMessage(botUserId, message.replyToMessageId)) { res ->
                handlers[res.get().date]?.handler?.onResult(Result.of(message))
            }
        }
    }

    internal fun init(chat: TdApi.Chat, client: SimpleTelegramClient) {
        botUserId = chat.id
        api = client

        client.addUpdateHandler(TdApi.UpdateMessageEdited::class.java) { update ->
            if (update.chatId == botUserId) {
                client.send(TdApi.GetMessage(botUserId, update.messageId)) { res ->
                    if (!res.isError && !res.get().isOutgoing) {
                        onMessageReceived(res.get())
                    }
                }
            }
        }

        client.addUpdateHandler(TdApi.UpdateNewMessage::class.java) { update ->
            if (update.message.chatId == botUserId && !update.message.isOutgoing) {
                onMessageReceived(update.message)
            }
        }

        toggleMuted(true)
    }

    internal fun reset() {
        api = null
        botUserId = 0L
        handlers.clear()
    }

    fun toggleMuted(muted: Boolean) {
        api?.send(TdApi.SetChatNotificationSettings(botUserId, TdApi.ChatNotificationSettings(
            !muted, Int.MAX_VALUE, true, 0L, true, true, true, true, true, true
        ))) {}
    }

    fun sendInlineQuery(
        query: String,
        userLocation: TdApi.Location? = null,
        offset: String? = null,
        resultHandler: GenericResultHandler<TdApi.InlineQueryResults>
    ) = api?.let { api ->
        api.getInlineQueryResults(botUserId, api.me.id, userLocation, query, offset, resultHandler)
    }

    fun sendMessage(
        content: TdApi.InputMessageContent,
        replyHandler: GenericResultHandler<TdApi.Message>
    ) = api?.sendMessage(botUserId, content = content) { res ->
        if (!res.isError) {
            handlers[res.get().date] = HandlerHolder(replyHandler)
        }
    }

    fun forwardMessage(
        message: TdApi.Message,
        replyHandler: GenericResultHandler<TdApi.Message>
    ) = api?.forwardMessages(botUserId, fromChatId = message.chatId, messageIds = arrayOf(message.id)) { res ->
        if (!res.isError) {
            handlers[res.get().messages.first().date] = HandlerHolder(replyHandler)
        }
    }

    private data class HandlerHolder(val handler: GenericResultHandler<TdApi.Message>) {
        val created = System.currentTimeMillis()
    }
}