package com.justai.jaicf.channel.td.scenario

import com.justai.jaicf.activator.regex.RegexActivatorContext
import com.justai.jaicf.builder.ScenarioDsl
import com.justai.jaicf.channel.td.*
import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.context.ActivatorContext
import com.justai.jaicf.plugin.StateBody
import com.justai.jaicf.plugin.StateDeclaration
import it.tdlight.jni.TdApi
import org.intellij.lang.annotations.Language
import java.util.*


@ScenarioDsl
@StateDeclaration
inline fun <reified U : TdApi.Update> TdScenarioRootBuilder.onUpdate(
    vararg conditions: OnlyIf,
    @StateBody noinline body: ActionContext<ActivatorContext, TdRequest<U>, TdReactions>.() -> Unit
) = state(UUID.randomUUID().toString()) {
    activators {
        update<U>().apply {
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
    @StateBody noinline body: ActionContext<ActivatorContext, TdNewMessageRequest<M>, TdReactions>.() -> Unit
) = state(UUID.randomUUID().toString()) {
    activators {
        update<TdApi.UpdateNewMessage>().apply {
            onlyIf { request.td?.message?.update?.message?.content is M }
            conditions.forEach(::onlyIf)
        }
    }

    action(tdNewMessageToken()) {
        body(this)
    }
}

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onAnyNewMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdNewMessageRequest<TdApi.MessageContent>, TdReactions>.() -> Unit
) = onNewMessage(*conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onNewTextMessage(
    @Language("RegExp") pattern: String,
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<RegexActivatorContext, TdNewTextMessageRequest, TdReactions>.() -> Unit
) = state(UUID.randomUUID().toString()) {
    activators {
        regex(pattern).apply {
            conditions.forEach(::onlyIf)
        }
    }

    action(tdNewTextMessage) {
        body(this)
    }
}

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onAnyNewTextMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<RegexActivatorContext, TdNewTextMessageRequest, TdReactions>.() -> Unit
) = onNewTextMessage(".*", *conditions, body = body)
