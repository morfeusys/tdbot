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
import com.justai.jaicf.plugin.StateBody
import com.justai.jaicf.plugin.StateDeclaration
import com.justai.jaicf.reactions.ButtonToState

typealias TdBotModel = TdBotScenarioRootBuilder.() -> Unit
typealias TdBotScenarioRootBuilder = RootBuilder<TelegramBotRequest, TelegramReactions>

@ScenarioDsl
fun createInteractiveScenario(
    @StateBody body: TdBotModel,
): Scenario = object : Scenario {
    override val model by lazy { createModel(telegram, body) }
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
        var startButton: ButtonToState? = null,
        var helpMarkdownText: String? = null,
    )
}