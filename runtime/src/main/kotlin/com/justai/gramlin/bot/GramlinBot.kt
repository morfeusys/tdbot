package com.justai.gramlin.bot

import com.justai.jaicf.BotEngine
import com.justai.jaicf.activator.regex.RegexActivator
import com.justai.jaicf.channel.invocationapi.InvocationEventRequest
import com.justai.jaicf.channel.td.TdChannel
import com.justai.jaicf.channel.td.api.TdBotApi
import com.justai.jaicf.channel.td.api.TdTelegramApi
import com.justai.jaicf.context.RequestContext
import com.justai.jaicf.helpers.kotlin.ifTrue
import com.justai.gramlin.bot.scenario.GramlinBotScenario
import com.justai.gramlin.runtime.AuthService
import com.justai.gramlin.runtime.InMemoryBotContextManager
import com.justai.gramlin.runtime.GramlinRuntime
import it.tdlight.client.APIToken
import it.tdlight.client.AuthenticationData
import it.tdlight.client.TDLibSettings
import it.tdlight.jni.TdApi
import org.slf4j.LoggerFactory
import java.nio.file.Paths
import java.util.concurrent.CompletableFuture

class GramlinBot(
    private val settings: GramlinRuntime.Settings,
    private val authService: AuthService,
) : TdBotApi {
    private val logger = LoggerFactory.getLogger("GramlinBot")
    private val me = CompletableFuture<TdApi.User>()

    private val tdSettings = TDLibSettings.create(APIToken(settings.apiId, settings.apiHash)).apply {
        databaseDirectoryPath = Paths.get(settings.tdDirectory,"bot")
        downloadedFilesDirectoryPath = Paths.get(settings.tdDirectory, "bot", "downloads")
    }

    private lateinit var channel: TdChannel
    private lateinit var user: TdApi.User

    override val chatId
        get() = this::user.isInitialized.ifTrue { user.id } ?: authService.userId.takeIf { it != 0L }

    override val api by lazy { channel.api }

    override fun sendEvent(event: String, data: String) {
        if (chatId != null) {
            channel.processInvocation(InvocationEventRequest(
                clientId = chatId.toString(),
                input = event,
                requestData = data
            ), RequestContext.DEFAULT)
        }
    }

    fun start(scenarios: Scenarios) {
        val botApi = BotEngine(
            scenario = GramlinBotScenario(authService, scenarios),
            activators = arrayOf(RegexActivator),
            defaultContextManager = InMemoryBotContextManager()
        )

        channel = TdChannel(
            botApi = botApi,
            authenticationData = AuthenticationData.bot(settings.tdBotToken),
            settings = tdSettings
        ).onReady { api ->
            me.complete(api.me)
        }.start()

        logger.info("Started")
    }

    fun getUser(): TdApi.User = me.get()

    fun onUserLoggedIn(api: TdTelegramApi) {
        logger.info("Ready")
        user = api.me
        sendEvent(GramlinBotScenario.EVENT_READY, user.id.toString())
    }

    fun onUserLoggedOut(api: TdTelegramApi) {
        logger.info("Closed")
        sendEvent(GramlinBotScenario.EVENT_CLOSE)
    }

    fun onException(error: Throwable) {
        logger.error("onException", error)
        sendEvent(GramlinBotScenario.EVENT_ERROR, error.message ?: "")
    }
}
