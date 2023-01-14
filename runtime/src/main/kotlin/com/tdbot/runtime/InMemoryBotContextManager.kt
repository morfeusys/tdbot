package com.tdbot.runtime

import com.justai.jaicf.api.BotRequest
import com.justai.jaicf.api.BotResponse
import com.justai.jaicf.context.BotContext
import com.justai.jaicf.context.DialogContext
import com.justai.jaicf.context.RequestContext
import com.justai.jaicf.context.manager.BotContextManager

internal class InMemoryBotContextManager : BotContextManager {
    private val storage = mutableMapOf<String, BotContext>()

    override fun loadContext(request: BotRequest, requestContext: RequestContext): BotContext {
        val bc = storage.computeIfAbsent(request.clientId) { clientId -> BotContext(clientId) }
        return bc.copy(dialogContext = bc.dialogContext.clone()).apply {
            result = bc.result
            client.putAll(bc.client)
            session.putAll(bc.session)
        }
    }

    override fun saveContext(
        botContext: BotContext,
        request: BotRequest?,
        response: BotResponse?,
        requestContext: RequestContext
    ) {
        storage[botContext.clientId] = botContext.copy(dialogContext = botContext.dialogContext.clone()).apply {
            result = botContext.result
            client.putAll(botContext.client)
            session.putAll(botContext.session)
        }
    }
}

private fun DialogContext.clone(): DialogContext {
    val dc = DialogContext()

    dc.nextContext = nextContext
    dc.currentContext = currentContext
    dc.nextState = nextState
    dc.currentState = currentState

    dc.transitions.apply {
        clear()
        putAll(transitions)
    }

    dc.backStateStack.apply {
        clear()
        addAll(backStateStack)
    }

    dc.transitionHistory.apply {
        clear()
        addAll(transitionHistory)
    }

    return dc
}