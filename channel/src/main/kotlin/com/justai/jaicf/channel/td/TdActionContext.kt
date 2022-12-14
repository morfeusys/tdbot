package com.justai.jaicf.channel.td

import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.context.ActivatorContext

typealias TdActionContext = ActionContext<out ActivatorContext, out TdRequest, TdReactions>
