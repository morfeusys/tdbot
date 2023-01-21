package com.justai.gramlin.api

import com.justai.jaicf.builder.RootBuilder
import com.justai.jaicf.builder.ScenarioDsl
import com.justai.jaicf.channel.invocationapi.invocationRequest
import com.justai.jaicf.channel.td.TdReactions
import com.justai.jaicf.channel.td.api.TdBotApi
import com.justai.jaicf.channel.td.request.DefaultTdRequest
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

abstract class GramlinInteractiveScenario(builder: Config.() -> Unit = {}) : Scenario {
    lateinit var gramlinBot: TdBotApi
    open val config = Config().apply(builder)

    abstract val interactiveScenario: Scenario

    protected fun sendInteractiveScenarioEvent(event: String, data: String = "") {
        if (this::gramlinBot.isInitialized) {
            gramlinBot.sendEvent(event, data)
        }
    }

    data class Config(
        var helpMarkdownText: String? = null,
        var actionButtons: List<ButtonToState> = emptyList(),
    )
}