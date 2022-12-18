package com.tdbot.api

import com.justai.jaicf.builder.RootBuilder
import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.client.forwardMessages
import com.justai.jaicf.channel.td.client.getInlineQueryResults
import com.justai.jaicf.channel.td.client.sendMessage
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.helpers.kotlin.ifTrue
import it.tdlight.client.GenericResultHandler
import it.tdlight.client.Result
import it.tdlight.client.SimpleTelegramClient
import it.tdlight.jni.TdApi
import org.slf4j.LoggerFactory

val isBotChat: (bot: BotClient?) -> OnlyIf = { isChat { it?.botUserId } }
val isNotBotChat: (bot: BotClient?) -> OnlyIf = { isNotChat { it?.botUserId } }

fun RootBuilder<DefaultTdRequest, TdReactions>.createBotClient(
    botName: String, sendStart: Boolean = true
) = BotClient(botName, sendStart).also { bot ->
        handle<TdReadyHook> { bot.init(api) }
    }

class BotClient(
    private val botName: String,
    private val sendStart: Boolean = true
) {
    private val logger = LoggerFactory.getLogger("BotClient_$botName")
    private lateinit var api: SimpleTelegramClient

    lateinit var chat: TdApi.Chat
        private set

    val botUserId
        get() = this::chat.isInitialized.ifTrue { chat.id } ?: 0L

    private val handlers = mutableMapOf<Int, HandlerHolder>()

    fun init(client: SimpleTelegramClient) {
        api = client.apply {
            send(TdApi.SearchPublicChat(botName)) { chat ->
                if (chat.isError) {
                    logger.error("Cannot find $botName bot", chat.error.message)
                } else {
                    init(chat.get())
                }
            }
        }
    }

    private fun init(chat: TdApi.Chat) {
        this.chat = chat
        if (sendStart) {
            api.sendMessage(chat.id, content = Td.text("/start"))
        }

        toggleMuted(true)

        api.addUpdateHandler(TdApi.UpdateMessageEdited::class.java) { update ->
            if (update.chatId == botUserId) {
                api.send(TdApi.GetMessage(chat.id, update.messageId)) { res ->
                    if (!res.isError && !res.get().isOutgoing) {
                        onMessageReceived(res.get())
                    }
                }
            }
        }
        api.addUpdateHandler(TdApi.UpdateNewMessage::class.java) { update ->
            if (update.message.chatId == botUserId && !update.message.isOutgoing) {
                onMessageReceived(update.message)
            }
        }
        api.addUpdateHandler(TdApi.UpdateAuthorizationState::class.java) { state ->
            if (state.authorizationState is TdApi.AuthorizationStateClosed) {
                handlers.clear()
            }
        }
    }

    private fun removeOutdatedHandlers(timeout: Long = 180000) = synchronized(handlers) {
        handlers
            .filterValues { it.created < System.currentTimeMillis() - timeout }
            .keys.forEach(handlers::remove)
    }

    private fun onMessageReceived(message: TdApi.Message) {
        removeOutdatedHandlers()
        if (message.replyToMessageId != 0L) {
            api.send(TdApi.GetMessage(botUserId, message.replyToMessageId)) { res ->
                handlers[res.get().date]?.handler?.onResult(Result.of(message))
            }
        }
    }

    fun toggleMuted(muted: Boolean) {
        api.send(TdApi.SetChatNotificationSettings(botUserId, TdApi.ChatNotificationSettings(
            !muted, Int.MAX_VALUE, true, 0L, true, true, true, true, true, true
        ))) {}
    }

    fun sendInlineQuery(
        query: String,
        userLocation: TdApi.Location? = null,
        offset: String? = null,
        resultHandler: GenericResultHandler<TdApi.InlineQueryResults>
    ) = api.getInlineQueryResults(botUserId, api.me.id, userLocation, query, offset, resultHandler)

    fun sendMessage(
        content: TdApi.InputMessageContent,
        replyHandler: GenericResultHandler<TdApi.Message>
    ) = api.sendMessage(botUserId, content = content) { res ->
        if (!res.isError) {
            handlers[res.get().date] = HandlerHolder(replyHandler)
        }
    }

    fun forwardMessage(
        message: TdApi.Message,
        replyHandler: GenericResultHandler<TdApi.Message>
    ) = api.forwardMessages(botUserId, fromChatId = message.chatId, messageIds = arrayOf(message.id)) { res ->
        if (!res.isError) {
            handlers[res.get().messages.first().date] = HandlerHolder(replyHandler)
        }
    }

    private data class HandlerHolder(val handler: GenericResultHandler<TdApi.Message>) {
        val created = System.currentTimeMillis()
    }
}