package com.tdbot.api

import com.justai.jaicf.builder.RootBuilder
import com.justai.jaicf.builder.ScenarioDsl
import com.justai.jaicf.builder.createModel
import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.hook.TdClosedHook
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.model.scenario.ScenarioModel
import com.justai.jaicf.plugin.StateBody
import it.tdlight.jni.TdApi
import org.slf4j.LoggerFactory

typealias BotClientTdModel = RootBuilder<TdRequest, TdReactions>.(bot: BotClient) -> Unit

@ScenarioDsl
fun TdBotClientScenario(
    botName: String,
    startBot: Boolean = true,
    @StateBody body: BotClientTdModel): Scenario = object : Scenario {
    override val model by lazy { createBotClientTdModel(botName, startBot, body) }
}

@ScenarioDsl
fun createBotClientTdModel(
    botName: String,
    startBot: Boolean = true,
    @StateBody body: BotClientTdModel
): ScenarioModel = createModel(td) {
    val logger = LoggerFactory.getLogger("BotClientScenario$botName")
    val botClient = BotClient()

    handle<TdReadyHook> {
        api.send(TdApi.SearchPublicChat(botName)) { chat ->
            if (chat.isError) {
                logger.error("Cannot find $botName bot", chat.error.message)
            } else {
                botClient.init(chat.get(), api)
                if (startBot) {
                    api.sendMessage(chat.get().id, content = Td.text("/start"))
                }
            }
        }
    }

    handle<TdClosedHook> {
        botClient.reset()
    }

    body(this, botClient)
}

