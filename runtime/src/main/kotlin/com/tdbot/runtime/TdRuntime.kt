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
import it.tdlight.client.AuthenticationData
import it.tdlight.client.ClientInteraction
import it.tdlight.client.TDLibSettings
import org.slf4j.LoggerFactory
import java.nio.file.Path

class TdRuntime(
    private val settings: Settings = defaultRuntimeSettings
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private lateinit var tdBot: TdBot
    private val linkInteraction = LinkClientInteraction(settings)

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

        tdBot = TdBot(settings, linkInteraction, scenarios)

        val settings = TDLibSettings.create(APIToken(settings.apiId, settings.apiHash)).apply {
            databaseDirectoryPath = Path.of(settings.tdDirectory)
        }

        val tdScenario = TdScenario(tdBot.getId(), scenarios)

        val tdEngine = BotEngine(
            scenario = tdScenario,
            activators = arrayOf(RegexActivator),
            defaultContextManager = MutableContextManager(),
        ).also { engine ->
            tdScenario.botApi = engine
        }

        startTdChannel(tdEngine, AuthenticationData.qrCode(), settings, linkInteraction)
    }

    private fun startTdChannel(
        tdEngine: BotEngine,
        authenticationData: AuthenticationData,
        settings: TDLibSettings,
        clientInteraction: ClientInteraction) {

        logger.info("Starting td channel")

        TdChannel(
            botApi = tdEngine,
            authenticationData = AuthenticationData.qrCode(),
            settings = settings,
            clientInteraction = linkInteraction
        )
            .onReady(tdBot::onReady).onClose(tdBot::onClose)
            .onClose { startTdChannel(tdEngine, authenticationData, settings, clientInteraction)
        }.start()
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

