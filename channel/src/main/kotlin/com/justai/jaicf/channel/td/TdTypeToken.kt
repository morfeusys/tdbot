package com.justai.jaicf.channel.td

import com.justai.jaicf.activator.regex.RegexActivatorContext
import com.justai.jaicf.context.ActivatorContext
import com.justai.jaicf.generic.ChannelTypeToken
import com.justai.jaicf.generic.ContextTypeToken
import it.tdlight.jni.TdApi

typealias TdTypeToken = ChannelTypeToken<DefaultTdRequest, TdReactions>

val td: TdTypeToken = ChannelTypeToken()

val tdNewTextMessage = ContextTypeToken<RegexActivatorContext, TdNewTextMessageRequest, TdReactions>()

fun <M : TdApi.MessageContent> tdNewMessageToken() =
    ContextTypeToken<ActivatorContext, TdNewMessageRequest<M>, TdReactions>()

fun <U : TdApi.Update> tdUpdateToken() =
    ContextTypeToken<ActivatorContext, TdRequest<U>, TdReactions>()