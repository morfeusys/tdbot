package com.tdbot.server.scenarios

import com.tdbot.scenario.ReplaceInOutgoingTextMessage
import com.tdbot.scenario.ReplaceOutgoingTextMessage
import com.tdbot.server.TdScenarioFactory
import com.tdbot.server.executeTemplate

data class ReplaceTextConfig(
    val pattern: String,
    val replacement: String,
    val replaceAll: Boolean = false,
)

val ReplaceText = TdScenarioFactory.create<ReplaceTextConfig>("replace_text.yaml") { config ->
    when (config.replaceAll) {
        true -> ReplaceOutgoingTextMessage(config.pattern) {
            config.replacement.executeTemplate(request)
        }
        else -> ReplaceInOutgoingTextMessage(config.pattern) {
            config.replacement.executeTemplate(request)
        }
    }
}