package com.justai.jaicf.channel.td.scenario

import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.hook.TdClosedHook
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.model.scenario.Scenario
import it.tdlight.jni.TdApi
import org.slf4j.LoggerFactory

fun Scenario.onlyIf(predicate: OnlyIf) = apply {
    model.transitions.forEach {
        it.rule.onlyIf(predicate)
    }
}

val Scenario.onlyIncoming get() = onlyIf(isIncoming)

val Scenario.onlyOutgoing get() = onlyIf(isOutgoing)

val Scenario.onlyChannelPosts get() = onlyIf(isChannelPost)

fun Scenario.onlyWithContact(query: String) = let { scenario ->
    val logger = LoggerFactory.getLogger("onlyWithContact")
    var userId: Long? = null

    TdScenario {
        handle<TdReadyHook> {
            api.send(TdApi.SearchContacts(query, 1)) { res ->
                if (res.isError || res.get().totalCount == 0) {
                    logger.error("Cannot find \"$query\" contact")
                } else {
                    userId = res.get().userIds.first()
                }
            }
        }

        handle<TdClosedHook> {
            userId = null
        }

        append(scenario.onlyIf(
            isChat { userId }
        ))
    }
}

fun Scenario.onlyInChat(query: String) = let { scenario ->
    val logger = LoggerFactory.getLogger("onlyInChat")
    var chatId: Long? = null

    TdScenario {
        handle<TdReadyHook> {
            api.send(TdApi.SearchChats(query, 1)) { res ->
                if (res.isError || res.get().totalCount == 0) {
                    logger.error("Cannot find \"$query\" chat")
                } else {
                    chatId = res.get().chatIds.first()
                }
            }
        }

        handle<TdClosedHook> {
            chatId = null
        }

        append(scenario.onlyIf(
            isChat { chatId }
        ))
    }
}