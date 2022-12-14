package com.tdbot.api

import com.justai.jaicf.builder.RootBuilder
import com.justai.jaicf.builder.ScenarioDsl
import com.justai.jaicf.builder.createModel
import com.justai.jaicf.channel.telegram.TelegramBotRequest
import com.justai.jaicf.channel.telegram.TelegramReactions
import com.justai.jaicf.channel.telegram.telegram
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.model.scenario.ScenarioModel
import com.justai.jaicf.plugin.StateBody

typealias TdBotModel = RootBuilder<TelegramBotRequest, TelegramReactions>.() -> Unit

@ScenarioDsl
fun createTdBotModel(
    @StateBody body: TdBotModel
): ScenarioModel = createModel(telegram, body)

@ScenarioDsl
fun TdBotScenario(
    @StateBody body: TdBotModel,
): Scenario = object : Scenario {
    override val model by lazy { createModel(telegram, body) }
}

interface TdInteractiveScenario : Scenario {
    val tdBotScenario: Scenario
    val helpMarkdownText: String
}