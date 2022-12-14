package com.tdbot.runtime

import com.justai.jaicf.api.BotRequest
import com.justai.jaicf.api.BotResponse
import com.justai.jaicf.context.BotContext
import com.justai.jaicf.context.RequestContext
import com.justai.jaicf.context.manager.BotContextManager

class MutableContextManager : BotContextManager {

    private val storage = mutableMapOf<String, BotContext>()

    override fun loadContext(request: BotRequest, requestContext: RequestContext) =
        storage.getOrPut(request.clientId) { BotContext(request.clientId) }

    override fun saveContext(
        botContext: BotContext,
        request: BotRequest?,
        response: BotResponse?,
        requestContext: RequestContext
    ) {}
}