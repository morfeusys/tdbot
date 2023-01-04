package com.tdbot.scenario.utils

import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.client.searchChats
import com.justai.jaicf.channel.td.scenario.*
import com.justai.jaicf.model.scenario.Scenario
import it.tdlight.jni.TdApi
import java.util.concurrent.atomic.AtomicReference

private fun TdScenarioRootBuilder.searchChannel(query: String) = AtomicReference<Long?>().also { ref ->
    onReady {
        api.searchChats(query).chatIds.firstOrNull()?.let(ref::set)
    }

    onClose {
        ref.set(null)
    }
}

private fun TdScenarioRootBuilder.searchContact(query: String) = AtomicReference<Long?>().also { ref ->
    onReady {
        api.send(TdApi.SearchContacts(query, 1)).userIds.firstOrNull()?.let(ref::set)
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

fun Scenario.onlyWithContacts(vararg contacts: String) = let { scenario ->
    TdScenario {
        val userIds = contacts.map(::searchContact)
        append(scenario.onlyIf(isChats { userIds.mapNotNull { it.get() }.toLongArray() }))
    }
}

fun Scenario.onlyWithNotContacts(vararg contacts: String) = let { scenario ->
    TdScenario {
        val userIds = contacts.map(::searchContact)
        append(scenario.onlyIf(isNotChats { userIds.mapNotNull { it.get() }.toLongArray() }))
    }
}

fun Scenario.onlyInChats(vararg chats: String) = let { scenario ->
    TdScenario {
        val chatIds = chats.map(::searchChannel)
        append(scenario.onlyIf(isChats { chatIds.mapNotNull { it.get() }.toLongArray() }))
    }
}

fun Scenario.onlyNotInChats(vararg chats: String) = let { scenario ->
    TdScenario {
        val chatIds = chats.map(::searchChannel)
        append(scenario.onlyIf(isNotChats { chatIds.mapNotNull { it.get() }.toLongArray() }))
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