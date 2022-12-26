package com.tdbot.runtime

import com.justai.jaicf.BotEngine
import com.justai.jaicf.channel.td.TdChannel
import com.justai.jaicf.channel.td.activator.RegexActivator
import com.justai.jaicf.model.scenario.Scenario
import com.tdbot.api.TdBotApi
import com.tdbot.bot.Scenarios
import com.tdbot.bot.TdBot
import com.tdbot.defaultRuntimeSettings
import it.tdlight.client.APIToken
import it.tdlight.client.TDLibSettings
import it.tdlight.client.TelegramError
import org.slf4j.LoggerFactory
import java.nio.file.Path

class TdRuntime(
    private val settings: Settings = defaultRuntimeSettings
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val authService = AuthService()
    private val tdSettings = TDLibSettings.create(APIToken(settings.apiId, settings.apiHash)).apply {
        databaseDirectoryPath = Path.of(settings.tdDirectory)
        downloadedFilesDirectoryPath = Path.of(settings.tdDirectory, "downloads")
    }

    private val tdBot: TdBot = TdBot(settings, authService)
    private lateinit var tdChannel: TdChannel

    fun start(builder: ScenariosBuilder.() -> Unit) {
        val scenarios = ScenariosBuilder(tdBot).apply {
            builder.invoke(this)
        }.build()

        tdBot.start(scenarios)
        val tdScenario = TdScenario(tdBot.getId(), scenarios)

        val tdEngine = BotEngine(
            scenario = tdScenario,
            activators = arrayOf(RegexActivator),
            defaultContextManager = MutableContextManager(),
        ).also { engine ->
            tdScenario.botApi = engine
        }

        startTdChannel(tdEngine)
    }

    private fun startTdChannel(tdEngine: BotEngine) {
        logger.info("Starting td channel")

        tdChannel = TdChannel(
            botApi = tdEngine,
            authenticationData = authService.authData,
            settings = tdSettings,
            clientInteraction = authService
        )
            .onReady(tdBot::onReady)
            .onClose(tdBot::onClose)
            .onClose { startTdChannel(tdEngine) }
            .onException(tdBot::onException)
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

    class ScenariosBuilder(
        val tdBotApi: TdBotApi,
    ) {
        private val map = mutableMapOf<String, Scenario>()

        infix fun String.to(scenario: Scenario) {
            if (map.containsKey(this)) {
                throw IllegalArgumentException("There is another scenario exists with name $this")
            } else {
                map[this] = scenario
            }
        }

        internal fun build() = Scenarios(map.toMap())
    }

    data class Settings(
        val apiId: Int,
        val apiHash: String,
        val tdBotToken: String,
        val tdDirectory: String,
        val publicQrLink: String,
    )
}

