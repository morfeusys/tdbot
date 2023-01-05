package com.tdbot.api

import com.justai.jaicf.builder.RootBuilder
import com.justai.jaicf.builder.ScenarioDsl
import com.justai.jaicf.builder.createModel
import com.justai.jaicf.channel.invocationapi.invocationRequest
import com.justai.jaicf.channel.telegram.TelegramBotRequest
import com.justai.jaicf.channel.telegram.TelegramReactions
import com.justai.jaicf.channel.telegram.telegram
import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.context.ActivatorContext
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.model.scenario.ScenarioModel
import com.justai.jaicf.plugin.StateBody
import com.justai.jaicf.plugin.StateDeclaration

typealias TdBotModel = RootBuilder<TelegramBotRequest, TelegramReactions>.() -> Unit
typealias TdBotScenarioRootBuilder = RootBuilder<TelegramBotRequest, TelegramReactions>

@ScenarioDsl
fun createTdBotModel(
    @StateBody body: TdBotModel
): ScenarioModel = createModel(telegram, body)

@ScenarioDsl
fun TdBotScenario(
    @StateBody body: TdBotModel,
): Scenario = object : Scenario {
    override val model by lazy { createTdBotModel(body) }
}

@ScenarioDsl
@StateDeclaration
fun TdBotScenarioRootBuilder.event(
    event: String,
    @StateBody body: ActionContext<ActivatorContext, TelegramBotRequest, TelegramReactions>.(String) -> Unit
) = state(event) {
    activators {
        event(event)
    }

    action(telegram) {
        body(this, request.invocationRequest?.requestData.orEmpty())
    }
}

abstract class TdInteractiveScenario : Scenario {
    lateinit var tdBotApi: TdBotApi
    abstract val helpMarkdownText: String
    abstract val tdBotScenario: Scenario

    protected fun sendTdBotEvent(event: String, data: String = "") {
        if (this::tdBotApi.isInitialized) {
            tdBotApi.sendEvent(event, data)
        }
    }
}