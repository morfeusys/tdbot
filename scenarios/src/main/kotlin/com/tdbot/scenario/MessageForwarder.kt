package com.tdbot.scenario

import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.channel.td.isIncoming
import com.justai.jaicf.channel.td.isNotChat
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onAnyNewMessage
import com.justai.jaicf.channel.td.scenario.onAnyNewTextMessage
import com.justai.jaicf.helpers.kotlin.ifTrue
import it.tdlight.jni.TdApi
import org.slf4j.LoggerFactory

fun MessageForwarder(toChat: String) = TdScenario {
    var chatId: Long? = null

    handle<TdReadyHook> {
        val logger = LoggerFactory.getLogger("MessageForwarder")

        api.send(TdApi.SearchChats(toChat, 1)) { res1 ->
            logger.info("Looking for $toChat chat")
            if (res1.isError || res1.get().totalCount == 0) {
                logger.info("Looking for $toChat chat on server")
                api.send(TdApi.SearchChatsOnServer(toChat, 1)) { res2 ->
                    if (res2.isError || res2.get().totalCount == 0) {
                        logger.error("Cannot find \"$toChat\" chat",
                            res2.isError.ifTrue { res2.error.message } ?: res1.isError.ifTrue { res1.error.message }
                        )
                    } else {
                        chatId = res2.get().chatIds.first()
                        logger.info("Found $toChat chat, id = $chatId")
                    }
                }
            } else {
                chatId = res1.get().chatIds.first()
                logger.info("Found $toChat chat, id = $chatId")
            }
        }
    }

    onAnyNewMessage(isIncoming, isNotChat { chatId }) {
        chatId?.let { reactions.forward(it) }
    }

    onAnyNewTextMessage(isIncoming, isNotChat { chatId }) {
        chatId?.let { reactions.forward(it) }
    }
}