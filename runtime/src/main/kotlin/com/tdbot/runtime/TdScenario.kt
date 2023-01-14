package com.tdbot.runtime

import com.justai.jaicf.api.BotApi
import com.justai.jaicf.channel.td.api.TdTelegramApi
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.channel.td.request.chatId
import com.justai.jaicf.channel.td.request.fromId
import com.justai.jaicf.channel.td.request.td
import com.justai.jaicf.channel.td.scenario.createTdModel
import com.justai.jaicf.context.BotContext
import com.justai.jaicf.context.RequestContext
import com.justai.jaicf.context.manager.BotContextManager
import com.justai.jaicf.hook.BotHookException
import com.justai.jaicf.hook.BotRequestHook
import com.justai.jaicf.model.scenario.Scenario
import com.tdbot.api.createBotClient
import com.tdbot.bot.Scenarios
import it.tdlight.jni.TdApi
import java.util.*

class TdScenario(
    tdBotUser: TdApi.User,
    scenarios: Scenarios,
    contextManager: BotContextManager
) : Scenario {
    lateinit var botApi: BotApi
    lateinit var telegrapApi: TdTelegramApi

    override val model by lazy {
        val rootState = UUID.randomUUID().toString()

        fun BotContext.setState(state: String) {
            dialogContext.currentState = state
            dialogContext.currentContext = state
            contextManager.saveContext(this, null, null, RequestContext.DEFAULT)
        }

        createTdModel {
            val botFather = createBotClient("@BotFather") { bot ->
                bot.sendMessage("/setcommands")
                bot.sendMessage("@${tdBotUser.usernames.activeUsernames.first()}")
                bot.sendMessage("scenarios - Show scenarios list")
            }

            handle<BotRequestHook> {
                if (request.td?.chatId == tdBotUser.id || request.td?.fromId == tdBotUser.id) {
                    throw BotHookException("Rejected tdBot's request")
                }
                if (request.td?.chatId == botFather.botUserId) {
                    throw BotHookException("Rejected BotFather's chat request")
                }
            }

            handle<TdReadyHook> {
                telegrapApi = api
            }

            scenarios.all.forEach { (name, scenario) ->
                append("/$name", scenario, modal = true)
            }

            fallback(rootState) {
                try {
                    scenarios.enabled.keys.forEach { state ->
                        context.setState("/$state")
                        botApi.process(request, reactions, RequestContext.DEFAULT)
                    }
                } finally {
                    context.setState("/$rootState")
                }
            }
        }
    }
}