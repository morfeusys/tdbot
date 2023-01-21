package com.justai.gramlin.runtime

import com.justai.gramlin.bot.GramlinBot
import com.justai.gramlin.defaultRuntimeSettings
import com.justai.jaicf.BotEngine
import com.justai.jaicf.channel.td.TdChannel
import com.justai.jaicf.channel.td.activator.RegexActivator
import it.tdlight.client.APIToken
import it.tdlight.client.TDLibSettings
import it.tdlight.client.TelegramError
import org.slf4j.LoggerFactory
import java.nio.file.Paths

class GramlinRuntime(
    private val settings: Settings = defaultRuntimeSettings
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val authService = AuthService()
    private val tdSettings = TDLibSettings.create(APIToken(settings.apiId, settings.apiHash)).apply {
        databaseDirectoryPath = Paths.get(settings.tdDirectory, "user")
        downloadedFilesDirectoryPath = Paths.get(settings.tdDirectory, "user", "downloads")
    }

    private val gramlinBot: GramlinBot = GramlinBot(settings, authService)
    private lateinit var tdChannel: TdChannel

    fun start(builder: ScenariosBuilder.() -> Unit) {
        val scenarios = ScenariosBuilder(settings, gramlinBot).apply {
            builder.invoke(this)
        }.build()

        gramlinBot.start(scenarios)
        val contextManager = InMemoryBotContextManager()
        val gramlinScenario = GramlinScenario(gramlinBot.getUser(), scenarios, contextManager)

        val gramlinEngine = BotEngine(
            scenario = gramlinScenario,
            activators = arrayOf(RegexActivator),
            defaultContextManager = contextManager,
        ).also { engine ->
            gramlinScenario.botApi = engine
        }
        startGramlinChannel(gramlinEngine)
    }

    private fun startGramlinChannel(engine: BotEngine) {
        logger.info("Starting td channel")

        tdChannel = TdChannel(
            botApi = engine,
            authenticationData = authService.authData,
            settings = tdSettings,
            clientInteraction = authService
        )
            .onReady(gramlinBot::onUserLoggedIn)
            .onClose(gramlinBot::onUserLoggedOut)
            .onClose { startGramlinChannel(engine) }
            .onException(gramlinBot::onException)
            .onException(::onException)
            .start()
    }

    private fun onException(e: Throwable) {
        if (e is TelegramError) {
            when (e.errorMessage) {
                "PHONE_CODE_INVALID" -> tdChannel.close()
            }
        }
    }

    data class Settings(
        val apiId: Int,
        val apiHash: String,
        val tdBotToken: String,
        val tdDirectory: String,
    )
}

