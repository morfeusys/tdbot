package com.tdbot.server.scenarios

import com.tdbot.scenario.ForwardIncomingMessages
import com.tdbot.scenario.ForwardIncomingMessagesIncognito
import com.tdbot.server.TdScenarioFactory

data class ForwardMessageConfig(
    val to: String,
    val incognito: Boolean = false
)

val ForwardMessage
    = TdScenarioFactory.create<ForwardMessageConfig>("forward_message.yaml") { config ->
        when (config.incognito) {
            true -> ForwardIncomingMessagesIncognito(config.to, tdBotApi)
            else -> ForwardIncomingMessages(config.to)
        }
    }