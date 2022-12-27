package com.tdbot.scenario

import com.justai.jaicf.channel.td.isIncoming
import com.justai.jaicf.channel.td.isNotChat
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onAnyNewMessage
import com.justai.jaicf.channel.td.scenario.onAnyTextMessage
import com.justai.jaicf.channel.td.scenario.onReady
import com.tdbot.scenario.utils.searchChats
import org.slf4j.LoggerFactory

fun ForwardIncomingMessages(toChat: String) = TdScenario {
    var chatId: Long? = null

    onReady {
        val logger = LoggerFactory.getLogger("ForwardIncomingMessages_$toChat")

        api.searchChats(toChat) { res ->
            if (res.isError || res.get().totalCount == 0) {
                logger.error("Cannot find chat \"$toChat\"")
            } else {
                chatId = res.get().chatIds.first()
            }
        }
    }

    onAnyNewMessage(isIncoming, isNotChat { chatId }) {
        chatId?.let { reactions.forward(it) }
    }

    onAnyTextMessage(isIncoming, isNotChat { chatId }) {
        chatId?.let { reactions.forward(it) }
    }
}