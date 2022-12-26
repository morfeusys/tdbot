package com.justai.jaicf.channel.td.scenario

import com.justai.jaicf.channel.td.*
import com.justai.jaicf.model.scenario.Scenario
import it.tdlight.jni.TdApi
import java.util.concurrent.atomic.AtomicReference

private fun TdScenarioRootBuilder.searchChannel(query: String) = AtomicReference<Long?>().also { ref ->
    onReady {
        api.send(TdApi.SearchChats(query, 1)) { res ->
            if (!res.isError && res.get().totalCount > 0) {
                ref.set(res.get().chatIds.first())
            }
        }
    }

    onClose {
        ref.set(null)
    }
}

private fun TdScenarioRootBuilder.searchContact(query: String) = AtomicReference<Long?>().also { ref ->
    onReady {
        api.send(TdApi.SearchContacts(query, 1)) { res ->
            if (!res.isError && res.get().totalCount > 0) {
                ref.set(res.get().userIds.first())
            }
        }
    }

    onClose {
        ref.set(null)
    }
}

fun Scenario.onlyIf(predicate: OnlyIf) = apply {
    model.transitions.forEach {
        it.rule.onlyIf(predicate)
    }
}

val Scenario.onlyIncoming get() = onlyIf(isIncoming)

val Scenario.onlyOutgoing get() = onlyIf(isOutgoing)

val Scenario.onlyChannelPosts get() = onlyIf(isChannelPost)

val Scenario.onlyNonChannelPosts get() = onlyIf(isNotChannelPost)

fun Scenario.onlyWithContact(query: String) = let { scenario ->
    TdScenario {
        val userId = searchContact(query)
        append(scenario.onlyIf(isChat { userId.get() }))
    }
}

fun Scenario.onlyInChat(query: String) = let { scenario ->
    TdScenario {
        val chatId = searchChannel(query)
        append(scenario.onlyIf(isChat { chatId.get() }))
    }
}

fun Scenario.onlyNotInChat(query: String) = let { scenario ->
    TdScenario {
        val chatId = searchChannel(query)
        append(scenario.onlyIf(isNotChat { chatId.get() }))
    }
}