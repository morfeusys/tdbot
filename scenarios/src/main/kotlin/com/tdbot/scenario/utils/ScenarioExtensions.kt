package com.tdbot.scenario.utils

import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.scenario.*
import com.justai.jaicf.model.scenario.Scenario
import it.tdlight.jni.TdApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicReference

private val coroutineScope = GlobalScope

private fun TdScenarioRootBuilder.searchChannel(query: String) = AtomicReference<Long?>().also { ref ->
    onReady {
        coroutineScope.launch {
            api.searchChats(query).chatIds.firstOrNull()?.let(ref::set)
        }
    }

    onClose {
        ref.set(null)
    }
}

private fun TdScenarioRootBuilder.searchContact(query: String) = AtomicReference<Long?>().also { ref ->
    onReady {
        coroutineScope.launch {
            api.send(TdApi.SearchContacts(query, 1)).userIds.firstOrNull()?.let(ref::set)
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

val Scenario.onlyOnline get() = onlyInStatus<TdApi.UserStatusOnline>()

val Scenario.onlyOffline get() = onlyInStatus<TdApi.UserStatusOffline>()

val Scenario.onlyChannelPosts get() = onlyIf(isChannelPost)

val Scenario.onlyNonChannelPosts get() = onlyIf(isNotChannelPost)

fun Scenario.onlyWithContact(query: String) = let { scenario ->
    TdScenario {
        val userId = searchContact(query)
        append(scenario.onlyIf(isChat { userId.get() }))
    }
}

fun Scenario.onlyWithNotContact(query: String) = let { scenario ->
    TdScenario {
        val userId = searchContact(query)
        append(scenario.onlyIf(isNotChat { userId.get() }))
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

inline fun <reified S : TdApi.UserStatus> Scenario.onlyInStatus() = let { scenario ->
    TdScenario {
        var status: TdApi.UserStatus? = null
        onUpdate<TdApi.UpdateUserStatus> {
            if (request.update.userId == request.me.id) {
                status = request.update.status
            }
        }

        onClose {
            status = null
        }

        append(scenario.onlyIf { status is S })
    }
}