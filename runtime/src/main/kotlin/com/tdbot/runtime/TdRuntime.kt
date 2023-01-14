package com.tdbot.runtime

import com.justai.jaicf.BotEngine
import com.justai.jaicf.channel.td.TdChannel
import com.justai.jaicf.channel.td.activator.RegexActivator
import com.justai.jaicf.channel.td.hook.TdClosedHook
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.tdbot.bot.TdBot
import com.tdbot.defaultRuntimeSettings
import it.tdlight.client.APIToken
import it.tdlight.client.TDLibSettings
import it.tdlight.client.TelegramError
import org.slf4j.LoggerFactory
import java.nio.file.Paths

class TdRuntime(
    private val settings: Settings = defaultRuntimeSettings
) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val authService = AuthService()
    private val tdSettings = TDLibSettings.create(APIToken(settings.apiId, settings.apiHash)).apply {
        databaseDirectoryPath = Paths.get(settings.tdDirectory, "user")
        downloadedFilesDirectoryPath = Paths.get(settings.tdDirectory, "user", "downloads")
    }

    private val tdBot: TdBot = TdBot(settings, authService)
    private lateinit var tdChannel: TdChannel

    fun start(builder: ScenariosBuilder.() -> Unit) {
        val scenarios = ScenariosBuilder(settings, tdBot).apply {
            builder.invoke(this)
        }.build()

        tdBot.start(scenarios)
        val tdScenario = TdScenario(tdBot.getUser(), scenarios)

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
            .onReady { tdEngine.hooks.triggerHook(TdReadyHook(it)) }
            .onReady(tdBot::onReady)
            .onClose(tdBot::onClose)
            .onClose { tdEngine.hooks.triggerHook(TdClosedHook(it)) }
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

    data class Settings(
        val apiId: Int,
        val apiHash: String,
        val tdBotToken: String,
        val tdDirectory: String,
    )
}

