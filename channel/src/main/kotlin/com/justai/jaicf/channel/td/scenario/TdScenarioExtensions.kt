package com.justai.jaicf.channel.td.scenario

import com.justai.jaicf.activator.regex.RegexActivatorContext
import com.justai.jaicf.builder.ScenarioDsl
import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.hook.TdClosedHook
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.context.ActivatorContext
import com.justai.jaicf.plugin.StateBody
import com.justai.jaicf.plugin.StateDeclaration
import it.tdlight.jni.TdApi
import org.intellij.lang.annotations.Language
import java.util.*

@ScenarioDsl
fun TdScenarioRootBuilder.onReady(listener: TdReadyHook.() -> Unit) = handle(listener)

@ScenarioDsl
fun TdScenarioRootBuilder.onClose(listener: TdClosedHook.() -> Unit) = handle(listener)

@ScenarioDsl
@StateDeclaration
inline fun <reified U : TdApi.Update> TdScenarioRootBuilder.onUpdate(
    vararg conditions: OnlyIf,
    @StateBody noinline body: ActionContext<ActivatorContext, TdRequest<U>, TdReactions>.() -> Unit
) = state(UUID.randomUUID().toString()) {
    activators {
        when (U::class.java) {
            TdApi.Update::class.java -> catchAll().onlyIf { request.td != null }
            else -> update<U>()
        }.apply {
            conditions.forEach(::onlyIf)
        }
    }

    action(tdUpdateToken<U>()) {
        body(this)
    }
}

@ScenarioDsl
@StateDeclaration
inline fun <reified M : TdApi.MessageContent> TdScenarioRootBuilder.onNewMessage(
    vararg conditions: OnlyIf,
    @StateBody noinline body: ActionContext<ActivatorContext, TdMessageRequest<M>, TdReactions>.() -> Unit
) = state(UUID.randomUUID().toString()) {
    activators {
        when (M::class.java) {
            TdApi.MessageText::class.java -> catchAll().onlyIf { request.td?.text != null }
            TdApi.MessageContent::class.java -> catchAll().onlyIf { request.td?.message != null }
            else -> update<TdApi.UpdateNewMessage>().onlyIf { request.td?.message?.update?.message?.content is M }
        }.apply {
            conditions.forEach(::onlyIf)
        }
    }

    action(tdMessageToken()) {
        body(this)
    }
}

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onAnyUpdate(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.Update>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onAnyMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageContent>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onTextMessage(
    @Language("RegExp") pattern: String,
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<RegexActivatorContext, TdTextMessageRequest, TdReactions>.() -> Unit
) = state(UUID.randomUUID().toString()) {
    activators {
        regex(pattern).apply {
            conditions.forEach(::onlyIf)
        }
    }

    action(tdRegexMessage) {
        body(this)
    }
}