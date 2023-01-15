package com.tdbot.scenario

import com.justai.jaicf.channel.td.api.searchChats
import com.justai.jaicf.channel.td.isIncoming
import com.justai.jaicf.channel.td.isNotChat
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onAnyMessage
import com.justai.jaicf.channel.td.scenario.onReady

fun ForwardIncomingMessages(toChat: String) = TdScenario {
    var chatId: Long? = null

    onReady {
        chatId = api.searchChats(toChat).chatIds.firstOrNull()
    }

    onAnyMessage(isIncoming, isNotChat { chatId }) {
        chatId?.let { reactions.forwardMessage(request.update.message, it) }
    }
}