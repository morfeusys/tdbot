package com.tdbot.api

import com.justai.jaicf.builder.RootBuilder
import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.client.*
import com.justai.jaicf.channel.td.hook.TdReadyHook
import it.tdlight.client.GenericResultHandler
import it.tdlight.client.Result
import it.tdlight.jni.TdApi
import org.slf4j.LoggerFactory

val isBotChat: (bot: BotClient?) -> OnlyIf = { isChat { it?.botUserId } }
val isNotBotChat: (bot: BotClient?) -> OnlyIf = { isNotChat { it?.botUserId } }

fun RootBuilder<DefaultTdRequest, TdReactions>.createBotClient(botName: String, initHandler: (client: BotClient) -> Unit = {}) =
    BotClient(botName, initHandler).also { bot ->
        handle<TdReadyHook> { bot.init(api) }
    }

class BotClient(
    private val botName: String,
    private val initHandler: (client: BotClient) -> Unit = {}
) {
    private val logger = LoggerFactory.getLogger("BotClient_$botName")
    private val handlers = mutableMapOf<Int, HandlerHolder>()
    private lateinit var api: TdTelegramApi

    var started = false
        private set

    var botUserId = 0L
        private set

    fun init(api: TdTelegramApi) {
        this.api = api.apply {
            logger.info("Looking for $botName bot")

            send(TdApi.SearchChats(botName, 1)) { res ->
                if (res.isError || res.get().totalCount == 0) {
                    logger.info("Looking for $botName bot on server")
                    send(TdApi.SearchPublicChat(botName)) { chat ->
                        if (chat.isError) {
                            logger.error("Cannot find $botName bot", chat.error.message)
                        } else {
                            logger.info("Found $botName id = ${chat.get().id}")
                            start { init(chat.get().id) }
                        }
                    }
                } else {
                    logger.info("Found $botName bot, id = ${res.get().chatIds.first()}")
                    init(res.get().chatIds.first())
                }
            }
        }
    }

    fun start(handler: GenericResultHandler<TdApi.Message> = DefaultResultHandler()) {
        if (!started) {
            api.sendMessage(botUserId, content = Td.text("/start")) { res ->
                started = true
                handler.onResult(res)
            }
        }
    }

    private fun init(chatId: Long) {
        botUserId = chatId
        toggleMuted(true)

        api.client.addUpdateHandler(TdApi.UpdateMessageEdited::class.java) { update ->
            if (update.chatId == botUserId) {
                api.send(TdApi.GetMessage(botUserId, update.messageId)) { res ->
                    if (!res.isError && !res.get().isOutgoing) {
                        onMessageReceived(res.get())
                    }
                }
            }
        }
        api.client.addUpdateHandler(TdApi.UpdateNewMessage::class.java) { update ->
            if (update.message.chatId == botUserId && !update.message.isOutgoing) {
                onMessageReceived(update.message)
            }
        }
        api.client.addUpdateHandler(TdApi.UpdateAuthorizationState::class.java) { state ->
            if (state.authorizationState is TdApi.AuthorizationStateClosed) {
                handlers.clear()
            }
        }

        initHandler(this)
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
    ) = api.getInlineQueryResults(botUserId, api.client.me.id, userLocation, query, offset, resultHandler)

    fun sendMessage(
        content: String,
        replyHandler: GenericResultHandler<TdApi.Message>
    ) = sendMessage(Td.text(content), replyHandler)

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