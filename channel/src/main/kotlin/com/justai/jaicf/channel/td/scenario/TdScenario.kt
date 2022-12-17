package com.justai.jaicf.channel.td

import com.justai.jaicf.builder.ActivationRulesBuilder
import com.justai.jaicf.builder.RootBuilder
import com.justai.jaicf.builder.ScenarioDsl
import com.justai.jaicf.builder.createModel
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.model.scenario.ScenarioModel
import com.justai.jaicf.plugin.StateBody
import it.tdlight.jni.TdApi

typealias TdModel = RootBuilder<DefaultTdRequest, TdReactions>.() -> Unit

fun <R : TdApi.Update> event(update: R): String = update::class.java.name
inline fun <reified R : TdApi.Update> event(): String = R::class.java.name
inline fun <reified R : TdApi.Update> ActivationRulesBuilder.update() = event(event<R>())

@ScenarioDsl
fun TdScenario(
    @StateBody body: TdModel,
): Scenario = object : Scenario {
    override val model by lazy { createTdModel(body) }
}

@ScenarioDsl
fun createTdModel(
    @StateBody body: TdModel
): ScenarioModel = createModel(td, body)
