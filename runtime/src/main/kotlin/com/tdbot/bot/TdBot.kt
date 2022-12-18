package com.tdbot.bot

import com.google.gson.JsonObject
import com.justai.jaicf.BotEngine
import com.justai.jaicf.activator.regex.RegexActivator
import com.justai.jaicf.channel.invocationapi.InvocationEventRequest
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.channel.telegram.TelegramChannel
import com.justai.jaicf.context.RequestContext
import com.tdbot.runtime.LinkClientInteraction
import com.tdbot.runtime.MutableContextManager
import com.tdbot.runtime.TdRuntime
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import it.tdlight.client.SimpleTelegramClient
import it.tdlight.jni.TdApi
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture

class TdBot(
    settings: TdRuntime.Settings,
    linkClientInteraction: LinkClientInteraction,
    scenarios: Scenarios
) {
    private val logger = LoggerFactory.getLogger(javaClass.name)
    private val botId = CompletableFuture<Long>()
    private val botApi = BotEngine(
        scenario = TdBotScenario(linkClientInteraction, scenarios),
        activators = arrayOf(RegexActivator),
        defaultContextManager = MutableContextManager()
    )

    private val channel = TelegramChannel(botApi, settings.tdBotToken).apply {
        run()
    }

    private lateinit var me: TdApi.User

    init {
        logger.info("Initializing...")
        HttpClient(CIO) {
            expectSuccess = true
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }.use { client ->
            runBlocking {
                client.get<JsonObject>("https://api.telegram.org/bot${settings.tdBotToken}/getMe").let { json ->
                    botId.complete(
                        json.getAsJsonObject("result").get("id").asLong
                    )
                }
            }
        }
    }

    fun getId(): Long = botId.get()

    fun onReady(client: SimpleTelegramClient) {
        logger.info("Ready")
        client.addDefaultExceptionHandler(::onException)
        client.send(TdApi.GetMe()) { res ->
            me = res.get()
            botApi.hooks.triggerHook(TdReadyHook(client, me))
            invoke("ready")
        }
    }

    fun onClose(client: SimpleTelegramClient) {
        logger.info("Closed")
        invoke("close")
    }

    private fun onException(error: Throwable) {
        logger.error("onException", error)
        invoke("error", error.message ?: "")
    }

    fun invoke(event: String, requestData: String = "") =
        channel.processInvocation(InvocationEventRequest(
            clientId = me.id.toString(),
            input = event,
            requestData = requestData
        ), RequestContext.DEFAULT)
}
