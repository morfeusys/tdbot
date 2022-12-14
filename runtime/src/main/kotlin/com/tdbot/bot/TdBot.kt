package com.tdbot.bot

import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.User
import com.justai.jaicf.BotEngine
import com.justai.jaicf.activator.regex.RegexActivator
import com.justai.jaicf.channel.invocationapi.InvocationEventRequest
import com.justai.jaicf.channel.td.api.TdTelegramApi
import com.justai.jaicf.channel.td.hook.TdClosedHook
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.channel.telegram.TelegramChannel
import com.justai.jaicf.context.RequestContext
import com.justai.jaicf.helpers.kotlin.ifTrue
import com.tdbot.api.TdBotApi
import com.tdbot.bot.scenario.TdBotScenario
import com.tdbot.runtime.AuthService
import com.tdbot.runtime.MutableContextManager
import com.tdbot.runtime.TdRuntime
import it.tdlight.jni.TdApi
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture

class TdBot(
    private val settings: TdRuntime.Settings,
    private val authService: AuthService,
) : TdBotApi {
    private val logger = LoggerFactory.getLogger(javaClass.name)
    private val me = CompletableFuture<User>()

    private lateinit var channel: TelegramChannel
    private lateinit var user: TdApi.User

    private val userId
        get() = this::user.isInitialized.ifTrue { user.id } ?: authService.userId.takeIf { it != 0L }

    override val chatId
        get() = userId?.let { ChatId.fromId(it) }

    override val telegram by lazy { channel.bot }

    override fun sendEvent(event: String, data: String) {
        if (userId != null) {
            channel.processInvocation(InvocationEventRequest(
                clientId = userId.toString(),
                input = event,
                requestData = data
            ), RequestContext.DEFAULT)
        }
    }

    fun start(scenarios: Scenarios) {
        logger.info("Initializing...")

        val botApi = BotEngine(
            scenario = TdBotScenario(authService, scenarios),
            activators = arrayOf(RegexActivator),
            defaultContextManager = MutableContextManager()
        )

        channel = TelegramChannel(botApi, settings.tdBotToken).apply {
            start()
        }

        me.complete(
            channel.bot.getMe().first?.let { res ->
                res.body()?.result
            } ?: throw IllegalStateException("Cannot get tdbot data. Check your bot token.")
        )
    }

    fun getUser(): User = me.get()

    fun onReady(api: TdTelegramApi) {
        logger.info("Ready")
        user = api.me
        val engine = channel.botApi as BotEngine
        engine.hooks.triggerHook(TdReadyHook(api))
        sendEvent("ready")
    }

    fun onClose(api: TdTelegramApi) {
        logger.info("Closed")
        val engine = channel.botApi as BotEngine
        engine.hooks.triggerHook(TdClosedHook(api))
        sendEvent("close")
    }

    fun onException(error: Throwable) {
        logger.error("onException", error)
        sendEvent("error", error.message ?: "")
    }
}
