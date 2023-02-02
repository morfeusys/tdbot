package com.justai.jaicf.channel.td.scenario

import com.justai.jaicf.activator.regex.RegexActivatorContext
import com.justai.jaicf.builder.ScenarioDsl
import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.hook.TdClosedHook
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.channel.td.request.TdMessageRequest
import com.justai.jaicf.channel.td.request.TdRequest
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
        tdUpdate<U>().apply {
            conditions.forEach(::onlyIf)
        }
    }

    action(tdUpdateType<U>()) {
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
        tdMessage<M>().apply {
            conditions.forEach(::onlyIf)
        }
    }

    action(tdMessageType()) {
        body(this)
    }
}

@ScenarioDsl
@StateDeclaration
inline fun <reified M : TdApi.MessageContent> TdScenarioRootBuilder.onNewMessage(
    @Language("RegExp") pattern: String,
    vararg conditions: OnlyIf,
    @StateBody noinline body: ActionContext<RegexActivatorContext, TdMessageRequest<M>, TdReactions>.() -> Unit
) = state(UUID.randomUUID().toString()) {
    activators {
        tdMessage<M>(pattern).apply {
            conditions.forEach(::onlyIf)
        }
    }

    action(tdRegexMessageType()) {
       body(this)
    }
}

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onAnyUpdate(
    vararg conditions: OnlyIf,
    @StateBody body: TdActionContext.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onAnyMessage(
    vararg conditions: OnlyIf,
    @StateBody body: TdMessageActionContext.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onAnyMessage(
    @Language("RegExp") pattern: String,
    vararg conditions: OnlyIf,
    @StateBody body: TdMessageActionContext.() -> Unit
) = onNewMessage(pattern, conditions = conditions, body = body)
