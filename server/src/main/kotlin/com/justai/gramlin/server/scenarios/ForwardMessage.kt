package com.justai.gramlin.server.scenarios

import com.justai.gramlin.scenario.ForwardIncomingMessages
import com.justai.gramlin.scenario.ForwardIncomingMessagesIncognito
import com.justai.gramlin.server.ScenarioFactory

data class ForwardMessageConfig(
    val to: String,
    val incognito: Boolean = false
)

val ForwardMessage
    = ScenarioFactory.create<ForwardMessageConfig>("forward_message.yaml") { config ->
        when (config.incognito) {
            true -> ForwardIncomingMessagesIncognito(config.to, gramlinBot)
            else -> ForwardIncomingMessages(config.to)
        }
    }