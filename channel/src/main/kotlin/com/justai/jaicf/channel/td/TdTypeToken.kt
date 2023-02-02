package com.justai.jaicf.channel.td

import com.justai.jaicf.activator.regex.RegexActivatorContext
import com.justai.jaicf.channel.td.request.DefaultTdRequest
import com.justai.jaicf.channel.td.request.TdMessageRequest
import com.justai.jaicf.channel.td.request.TdRequest
import com.justai.jaicf.context.ActivatorContext
import com.justai.jaicf.generic.ChannelTypeToken
import com.justai.jaicf.generic.ContextTypeToken
import it.tdlight.jni.TdApi

typealias TdTypeToken = ChannelTypeToken<DefaultTdRequest, TdReactions>

val td: TdTypeToken = ChannelTypeToken()

fun <M : TdApi.MessageContent> tdRegexMessageType() =
    ContextTypeToken<RegexActivatorContext, TdMessageRequest<M>, TdReactions>()

fun <M : TdApi.MessageContent> tdMessageType() =
    ContextTypeToken<ActivatorContext, TdMessageRequest<M>, TdReactions>()

fun <U : TdApi.Update> tdUpdateType() =
    ContextTypeToken<ActivatorContext, TdRequest<U>, TdReactions>()