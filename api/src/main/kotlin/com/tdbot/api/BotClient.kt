package com.tdbot.api

import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.api.*
import com.justai.jaicf.channel.td.scenario.TdScenarioRootBuilder
import com.justai.jaicf.channel.td.scenario.onClose
import com.justai.jaicf.channel.td.scenario.onReady
import it.tdlight.jni.TdApi
import org.slf4j.LoggerFactory

val isBotChat: (bot: BotClient?) -> OnlyIf = { isChat { it?.botUserId } }
val isNotBotChat: (bot: BotClient?) -> OnlyIf = { isNotChat { it?.botUserId } }

fun TdScenarioRootBuilder.createBotClient(botName: String, readyHandler: (client: BotClient) -> Unit = {}) =
    BotClient(botName, readyHandler).also { bot ->
        onReady { bot.init(api) }
        onClose { bot.stop() }
    }

class BotClient(
    private val botName: String,
    private val readyHandler: (client: BotClient) -> Unit = {}
) {
    private val logger = LoggerFactory.getLogger("BotClient-$botName")
    private val handlers = mutableMapOf<Int, HandlerHolder>()
    private lateinit var api: TdTelegramApi
    private var startMessage: TdApi.Message? = null

    val isStarted
        get() = startMessage != null

    var botUserId = 0L
        private set

    fun init(api: TdTelegramApi) {
        this.api = api
        logger.info("Looking for $botName bot")
        var start = false
        var chats = api.searchChats(botName)
        if (chats.totalCount == 0) {
            logger.info("Looking for $botName public chat")
            chats = api.send(TdApi.SearchPublicChats(botName))
            start = chats.totalCount > 0
        }

        if (chats.totalCount == 0) {
            logger.error("Cannot find $botName bot")
        } else {
            botUserId = chats.chatIds.first()
            logger.info("Found $botName bot, id = $botUserId")
            init(start)
        }
    }

    internal fun stop() {
        startMessage = null
        handlers.clear()
    }

    fun start(): TdApi.Message {
        if (!isStarted) {
            startMessage = api.sendMessage(botUserId, content = TdMessage.text("/start"))
        }
        return startMessage!!
    }

    private fun init(start: Boolean) {
        if (start) {
            start()
        }

        toggleMuted(true)

        api.onUpdate<TdApi.UpdateMessageEdited> { update ->
            if (update.chatId == botUserId) {
                api.send(TdApi.GetMessage(botUserId, update.messageId)).let { res ->
                    if (!res.isOutgoing) {
                        onMessageReceived(res)
                    }
                }
            }
        }

        api.onUpdate<TdApi.UpdateNewMessage> { update ->
            if (update.message.chatId == botUserId && !update.message.isOutgoing) {
                onMessageReceived(update.message)
            }
        }

        readyHandler(this)
    }

    private fun removeOutdatedHandlers(timeout: Long = 180000) = synchronized(handlers) {
        handlers
            .filterValues { it.created < System.currentTimeMillis() - timeout }
            .keys.forEach(handlers::remove)
    }

    private fun onMessageReceived(message: TdApi.Message) {
        removeOutdatedHandlers()
        if (message.replyToMessageId != 0L) {
            val msg = api.send(TdApi.GetMessage(botUserId, message.replyToMessageId))
            handlers[msg.date]?.handler?.invoke(message)
        }
    }

    fun toggleMuted(muted: Boolean) {
        api.send(TdApi.SetChatNotificationSettings(botUserId, TdApi.ChatNotificationSettings(
            !muted, Int.MAX_VALUE, true, 0L, true, true, true, true, true, true
        )))
    }

    fun sendInlineQuery(
        query: String,
        userLocation: TdApi.Location? = null,
        offset: String? = null,
    ) = api.getInlineQueryResults(botUserId, api.me.id, userLocation, query, offset)

    fun sendMessage(
        content: String,
        replyHandler: (TdApi.Message) -> Unit = {}
    ) = sendMessage(TdMessage.text(content), replyHandler)

    fun sendMessage(
        content: TdApi.InputMessageContent,
        replyHandler: (TdApi.Message) -> Unit = {}
    ) = api.sendMessage(botUserId, content = content).let { msg ->
        handlers[msg.date] = HandlerHolder(replyHandler)
    }

    fun forwardMessage(
        message: TdApi.Message,
        replyHandler: (TdApi.Message) -> Unit = {}
    ) = api.forwardMessages(botUserId, fromChatId = message.chatId, messageIds = arrayOf(message.id)).let { msgs ->
        handlers[msgs.messages.first().date] = HandlerHolder(replyHandler)
    }

    private data class HandlerHolder(val handler: (TdApi.Message) -> Unit) {
        val created = System.currentTimeMillis()
    }
}