package com.tdbot.bot

import com.justai.jaicf.BotEngine
import com.justai.jaicf.activator.regex.RegexActivator
import com.justai.jaicf.channel.invocationapi.InvocationEventRequest
import com.justai.jaicf.channel.td.client.TdTelegramApi
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.context.RequestContext
import com.justai.jaicf.helpers.kotlin.ifTrue
import com.tdbot.bot.channel.TelegramChannel
import com.tdbot.bot.scenario.TdBotScenario
import com.tdbot.runtime.AuthService
import com.tdbot.runtime.MutableContextManager
import com.tdbot.runtime.TdRuntime
import it.tdlight.jni.TdApi
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture

class TdBot(
    settings: TdRuntime.Settings,
    private val authService: AuthService,
    scenarios: Scenarios
) {
    private val logger = LoggerFactory.getLogger(javaClass.name)
    private val botId = CompletableFuture<Long>()
    private val botApi = BotEngine(
        scenario = TdBotScenario(authService, scenarios),
        activators = arrayOf(RegexActivator),
        defaultContextManager = MutableContextManager()
    )

    private val channel = TelegramChannel(botApi, settings.tdBotToken).apply {
        run()
    }

    private lateinit var user: TdApi.User

    init {
        logger.info("Initializing...")
        val id = channel.bot.getMe().first?.let { res ->
            res.body()?.result?.id
        } ?: throw IllegalStateException("Cannot get tdbot data. Check your bot token.")
        botId.complete(id)
    }

    fun getId(): Long = botId.get()

    fun onReady(api: TdTelegramApi) {
        logger.info("Ready")
        api.send(TdApi.GetMe()) { res ->
            user = res.get()
            botApi.hooks.triggerHook(TdReadyHook(api, user))
            invoke("ready")
        }
    }

    fun onClose(api: TdTelegramApi) {
        logger.info("Closed")
        invoke("close")
    }

    fun onException(error: Throwable) {
        logger.error("onException", error)
        invoke("error", error.message ?: "")
    }

    fun invoke(event: String, requestData: String = "") {
        val userId = this::user.isInitialized.ifTrue { user.id } ?: authService.userId.takeIf { it != 0L }
        if (userId != null) {
            channel.processInvocation(InvocationEventRequest(
                clientId = userId.toString(),
                input = event,
                requestData = requestData
            ), RequestContext.DEFAULT)
        }
    }
}
