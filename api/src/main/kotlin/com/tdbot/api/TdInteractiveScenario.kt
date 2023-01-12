package com.tdbot.api

import com.justai.jaicf.builder.RootBuilder
import com.justai.jaicf.builder.ScenarioDsl
import com.justai.jaicf.builder.createModel
import com.justai.jaicf.channel.invocationapi.invocationRequest
import com.justai.jaicf.channel.td.TdReactions
import com.justai.jaicf.channel.td.request.DefaultTdRequest
import com.justai.jaicf.channel.td.td
import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.context.ActivatorContext
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.plugin.StateBody
import com.justai.jaicf.plugin.StateDeclaration
import com.justai.jaicf.reactions.ButtonToState

typealias TdBotScenarioRootBuilder = RootBuilder<DefaultTdRequest, TdReactions>

@ScenarioDsl
@StateDeclaration
fun TdBotScenarioRootBuilder.event(
    event: String,
    @StateBody body: ActionContext<ActivatorContext, DefaultTdRequest, TdReactions>.(String) -> Unit
) = state(event) {
    activators {
        event(event)
    }

    action {
        body(this, request.invocationRequest?.requestData.orEmpty())
    }
}

abstract class TdInteractiveScenario(builder: Config.() -> Unit = {}) : Scenario {
    lateinit var tdBotApi: TdBotApi
    open val config = Config().apply(builder)

    abstract val interactiveScenario: Scenario

    protected fun sendInteractiveScenarioEvent(event: String, data: String = "") {
        if (this::tdBotApi.isInitialized) {
            tdBotApi.sendEvent(event, data)
        }
    }

    data class Config(
        var helpMarkdownText: String? = null,
        var actionButtons: List<ButtonToState> = emptyList(),
    )
}