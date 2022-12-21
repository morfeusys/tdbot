package com.tdbot.runtime

import com.justai.jaicf.BotEngine
import com.justai.jaicf.channel.td.TdChannel
import com.justai.jaicf.channel.td.activator.RegexActivator
import com.tdbot.bot.Scenarios
import com.tdbot.bot.TdBot
import com.justai.jaicf.model.scenario.Scenario
import com.tdbot.api.TdBotApi
import com.tdbot.defaultRuntimeSettings
import it.tdlight.client.APIToken
import it.tdlight.client.TDLibSettings
import it.tdlight.client.TelegramError
import it.tdlight.common.ExceptionHandler
import org.slf4j.LoggerFactory
import java.nio.file.Path
import kotlin.system.exitProcess

class TdRuntime(
    private val settings: Settings = defaultRuntimeSettings
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val authService = AuthService()
    private val tdSettings = TDLibSettings.create(APIToken(settings.apiId, settings.apiHash)).apply {
        databaseDirectoryPath = Path.of(settings.tdDirectory)
    }

    private lateinit var tdBot: TdBot
    private lateinit var tdChannel: TdChannel

    private val tdBotApi = object : TdBotApi {
        override fun sendEvent(event: String, data: String) {
            tdBot.invoke(event, data)
        }

        override fun sendMessage(message: String) =
            sendEvent("TdBotScenario.onMessage", message)
    }

    fun start(scenarios: ScenariosBuilder.() -> Unit) {
        val scenarios = ScenariosBuilder(tdBotApi).apply {
            scenarios.invoke(this)
        }.build()

        tdBot = TdBot(settings, authService, scenarios)

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

