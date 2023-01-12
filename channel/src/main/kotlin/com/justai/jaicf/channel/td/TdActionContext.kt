package com.justai.jaicf.channel.td

import com.justai.jaicf.activator.regex.RegexActivatorContext
import com.justai.jaicf.channel.td.request.DefaultTdRequest
import com.justai.jaicf.channel.td.request.TdMessageRequest
import com.justai.jaicf.channel.td.request.TdTextMessageRequest
import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.context.ActivatorContext
import it.tdlight.jni.TdApi

typealias TdActionContext = ActionContext<out ActivatorContext, out DefaultTdRequest, TdReactions>
typealias TdMessageActionContext = ActionContext<out ActivatorContext, out TdMessageRequest<out TdApi.MessageContent>, TdReactions>
typealias TdRegexMessageActionContext = ActionContext<RegexActivatorContext, TdTextMessageRequest, TdReactions>