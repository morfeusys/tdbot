package com.tdbot.runtime

import com.justai.jaicf.api.BotApi
import com.tdbot.bot.Scenarios
import com.justai.jaicf.channel.td.chatId
import com.justai.jaicf.channel.td.scenario.createTdModel
import com.justai.jaicf.channel.td.messageRequest
import com.justai.jaicf.channel.td.td
import com.justai.jaicf.context.BotContext
import com.justai.jaicf.context.RequestContext
import com.justai.jaicf.hook.BotHookException
import com.justai.jaicf.hook.BotRequestHook
import com.justai.jaicf.model.scenario.Scenario
import java.util.*

class TdScenario(
    tdBotId: Long,
    scenarios: Scenarios
) : Scenario {
    lateinit var botApi: BotApi

    override val model by lazy {
        val rootState = UUID.randomUUID().toString()

        createTdModel {
            handle<BotRequestHook> {
                if (request.td?.messageRequest?.chatId == tdBotId) {
                    throw BotHookException()
                }
            }

            scenarios.all.forEach { scenario ->
                append("/${scenario.key}", scenario.value, modal = true)
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