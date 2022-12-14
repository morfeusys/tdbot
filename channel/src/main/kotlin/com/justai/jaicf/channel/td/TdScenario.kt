package com.justai.jaicf.channel.td

import com.justai.jaicf.activator.regex.RegexActivatorContext
import com.justai.jaicf.activator.regex.regex
import com.justai.jaicf.builder.*
import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.model.scenario.ScenarioModel
import com.justai.jaicf.plugin.StateBody
import com.justai.jaicf.plugin.StateDeclaration
import it.tdlight.jni.TdApi
import org.intellij.lang.annotations.Language
import java.util.*

typealias TdModel = RootBuilder<TdRequest, TdReactions>.() -> Unit
typealias ScenarioExtensions = ScenarioGraphBuilder<TdRequest, TdReactions>

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

@ScenarioDsl
@StateDeclaration
inline fun <reified R : TdApi.Update> ScenarioExtensions.onUpdate(
    vararg conditions: OnlyIf,
    @StateBody noinline body: TdActionContext.(update: R) -> Unit
) = state(UUID.randomUUID().toString()) {
    activators {
        update<R>().apply {
            conditions.forEach(::onlyIf)
        }
    }

    action {
        body(this, request.update as R)
    }
}

@ScenarioDsl
@StateDeclaration
inline fun <reified M : TdApi.MessageContent> ScenarioExtensions.onNewMessage(
    vararg conditions: OnlyIf,
    @StateBody noinline body: TdActionContext.(update: TdApi.UpdateNewMessage, content: M) -> Unit
) = state(UUID.randomUUID().toString()) {
    activators {
        update<TdApi.UpdateNewMessage>().apply {
            onlyIf { request.td?.message?.update?.message?.content is M }
            conditions.forEach(::onlyIf)
        }
    }

    action {
        body(this, request.update as TdApi.UpdateNewMessage, request.message?.update?.message?.content as M)
    }
}

@ScenarioDsl
@StateDeclaration
fun ScenarioExtensions.onAnyNewMessage(
    vararg conditions: OnlyIf,
    @StateBody body: TdActionContext.(update: TdApi.UpdateNewMessage, content: TdApi.MessageContent) -> Unit
) = onNewMessage(*conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun ScenarioExtensions.onNewTextMessage(
    @Language("RegExp") pattern: String,
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<RegexActivatorContext, TdRequest, TdReactions>.(update: TdApi.UpdateNewMessage) -> Unit
) = state(UUID.randomUUID().toString()) {
    activators {
        regex(pattern).apply {
            conditions.forEach(::onlyIf)
        }
    }

    action(regex) {
        body(this, request.update as TdApi.UpdateNewMessage)
    }
}

@ScenarioDsl
@StateDeclaration
fun ScenarioExtensions.onAnyNewTextMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<RegexActivatorContext, TdRequest, TdReactions>.(update: TdApi.UpdateNewMessage, content: TdApi.MessageText) -> Unit
) = onNewTextMessage(".*", *conditions) { update ->
    body(this, update, update.message.content as TdApi.MessageText)
}
