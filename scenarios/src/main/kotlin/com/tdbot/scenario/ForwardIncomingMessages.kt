package com.tdbot.scenario

import com.justai.jaicf.channel.td.isIncoming
import com.justai.jaicf.channel.td.isNotChat
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onAnyMessage
import com.justai.jaicf.channel.td.scenario.onReady
import com.justai.jaicf.channel.td.scenario.onTextMessage
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

    onAnyMessage(isIncoming, isNotChat { chatId }) {
        chatId?.let { reactions.forward(it) }
    }

    onTextMessage(isIncoming, isNotChat { chatId }) {
        chatId?.let { reactions.forward(it) }
    }
}