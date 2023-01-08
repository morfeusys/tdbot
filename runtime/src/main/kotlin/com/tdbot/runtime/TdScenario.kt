package com.tdbot.runtime

import com.github.kotlintelegrambot.entities.User
import com.justai.jaicf.api.BotApi
import com.justai.jaicf.channel.td.client.TdTelegramApi
import com.justai.jaicf.channel.td.fromId
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.channel.td.messageId
import com.justai.jaicf.channel.td.scenario.createTdModel
import com.justai.jaicf.channel.td.senderId
import com.justai.jaicf.channel.td.td
import com.justai.jaicf.context.BotContext
import com.justai.jaicf.context.RequestContext
import com.justai.jaicf.hook.BotHookException
import com.justai.jaicf.hook.BotRequestHook
import com.justai.jaicf.model.scenario.Scenario
import com.tdbot.api.createBotClient
import com.tdbot.bot.Scenarios
import java.util.*

class TdScenario(tdBotUser: User, scenarios: Scenarios) : Scenario {
    lateinit var botApi: BotApi
    lateinit var telegrapApi: TdTelegramApi

    override val model by lazy {
        val rootState = UUID.randomUUID().toString()

        createTdModel {
            val botFather = createBotClient("@BotFather") { bot ->
                bot.sendMessage("/setcommands")
                bot.sendMessage("@${tdBotUser.username}")
                bot.sendMessage("start - Go to main menu\n" +
                        scenarios.all.keys.joinToString("\n") { "${it.lowercase()} - Open scenario menu" })
            }

            handle<BotRequestHook> {
                if (request.td?.fromId == tdBotUser.id) {
                    throw BotHookException("Rejected tdBot's request")
                }
                if (request.td?.fromId == botFather.botUserId) {
                    throw BotHookException("Rejected BotFather's request")
                }
                if (telegrapApi.isOutgoing(request.td)) {
                    throw BotHookException("Self message rejected [${request.td?.messageId}]")
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

    private fun BotContext.setState(state: String) {
        dialogContext.currentState = state
        dialogContext.currentContext = state
    }
}