package com.justai.gramlin.api

import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.api.searchAnyChats
import com.justai.jaicf.channel.td.api.searchPublicChats
import com.justai.jaicf.channel.td.scenario.*
import com.justai.jaicf.model.scenario.Scenario
import it.tdlight.jni.TdApi
import java.util.concurrent.atomic.AtomicReference

private fun Scenario.build(builder: TdScenarioRootBuilder.(Scenario) -> Unit) = let { scenario ->
    if (scenario is GramlinInteractiveScenario) {
        object : GramlinInteractiveScenario() {
            override val config = scenario.config
            override val interactiveScenario = scenario.interactiveScenario
            override val model = createTdModel { builder(this, scenario) }
        }
    } else {
        TdScenario {
            builder(this, scenario)
        }
    }
}

fun TdScenarioRootBuilder.searchAnyChat(query: String) = AtomicReference<Long?>().also { ref ->
    onReady { api.searchAnyChats(query).chatIds.firstOrNull()?.let(ref::set) }
}

fun TdScenarioRootBuilder.searchPublicChat(query: String) = AtomicReference<Long?>().also { ref ->
    onReady { api.searchPublicChats(query).chatIds.firstOrNull()?.let(ref::set) }
}

fun TdScenarioRootBuilder.searchContact(query: String) = AtomicReference<Long?>().also { ref ->
    onReady { api.send(TdApi.SearchContacts(query, 1)).userIds.firstOrNull()?.let(ref::set) }
}

fun Scenario.onlyIf(predicate: OnlyIf) = apply { model.transitions.forEach { it.rule.onlyIf(predicate) } }

val Scenario.onlyIncoming get() = onlyIf(isIncoming)

val Scenario.onlyOutgoing get() = onlyIf(isOutgoing)

val Scenario.onlyOnline get() = onlyInStatus<TdApi.UserStatusOnline>()

val Scenario.onlyOffline get() = onlyInStatus<TdApi.UserStatusOffline>()

val Scenario.onlyChannelPosts get() = onlyIf(isChannelPost)

val Scenario.onlyNonChannelPosts get() = onlyIf(isNotChannelPost)

fun Scenario.onlyWithContacts(vararg contacts: String) = build { scenario ->
    val userIds = contacts.map(::searchContact)
    append(scenario.onlyIf(isChats { userIds.mapNotNull { it.get() }.toLongArray() }))
}

fun Scenario.onlyWithNotContacts(vararg contacts: String) = build { scenario ->
    val userIds = contacts.map(::searchContact)
    append(scenario.onlyIf(isNotChats { userIds.mapNotNull { it.get() }.toLongArray() }))
}

fun Scenario.onlyInChats(vararg chats: String) = build { scenario ->
    val chatIds = chats.map(::searchAnyChat)
    append(scenario.onlyIf(isChats { chatIds.mapNotNull { it.get() }.toLongArray() }))
}

fun Scenario.onlyNotInChats(vararg chats: String) = build { scenario ->
    val chatIds = chats.map(::searchAnyChat)
    append(scenario.onlyIf(isNotChats { chatIds.mapNotNull { it.get() }.toLongArray() }))
}

private inline fun <reified S : TdApi.UserStatus> Scenario.onlyInStatus() = build { scenario ->
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