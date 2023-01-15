package com.justai.jaicf.channel.td.scenario

import com.justai.jaicf.builder.ActivationRulesBuilder
import com.justai.jaicf.builder.RootBuilder
import com.justai.jaicf.builder.ScenarioDsl
import com.justai.jaicf.builder.createModel
import com.justai.jaicf.channel.td.TdReactions
import com.justai.jaicf.channel.td.activator.asPattern
import com.justai.jaicf.channel.td.request.DefaultTdRequest
import com.justai.jaicf.channel.td.request.messageRequest
import com.justai.jaicf.channel.td.request.td
import com.justai.jaicf.channel.td.request.textMessageRequest
import com.justai.jaicf.channel.td.td
import com.justai.jaicf.channel.td.text
import com.justai.jaicf.model.activation.ActivationRule
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.model.scenario.ScenarioModel
import com.justai.jaicf.plugin.StateBody
import it.tdlight.jni.TdApi
import org.intellij.lang.annotations.Language

typealias TdScenarioRootBuilder = RootBuilder<DefaultTdRequest, TdReactions>
typealias TdModel = TdScenarioRootBuilder.() -> Unit

fun <R : TdApi.Update> tdEvent(update: R): String = update::class.java.name
inline fun <reified R : TdApi.Update> tdEvent(): String = R::class.java.name

inline fun <reified R : TdApi.Update> ActivationRulesBuilder.tdUpdate() = when (R::class.java) {
    TdApi.Update::class.java -> catchAll().onlyIf { request.td != null }
    else -> event(tdEvent<R>())
}

inline fun <reified R: TdApi.MessageContent> ActivationRulesBuilder.tdMessage(
    @Language("RegExp") textPattern: String? = null
): ActivationRule {
    val regex = textPattern?.asPattern?.toRegex()
    return when (R::class.java) {
        TdApi.MessageText::class.java -> regex?.let(::regex) ?: catchAll().onlyIf { request.td?.textMessageRequest != null }
        TdApi.MessageContent::class.java -> catchAll().onlyIf { request.td?.messageRequest?.let { regex.matches(it.content) } ?: false }
        else -> tdUpdate<TdApi.UpdateNewMessage>().onlyIf { request.td?.messageRequest?.let { it.content is R && regex.matches(it.content) } ?: false }
    }
}

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

@ScenarioDsl
fun createTdScenario(
    @StateBody body: TdModel
) = TdScenario(body)

fun Regex?.matches(content: TdApi.MessageContent) = when {
    this == null -> true
    content.text == null -> false
    else -> matches(content.text!!)
}