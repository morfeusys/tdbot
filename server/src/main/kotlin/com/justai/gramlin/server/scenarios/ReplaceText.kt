package com.justai.gramlin.server.scenarios

import com.justai.gramlin.scenario.ReplaceInOutgoingTextMessage
import com.justai.gramlin.scenario.ReplaceOutgoingTextMessage
import com.justai.gramlin.server.ScenarioFactory
import com.justai.gramlin.server.executeTemplate

data class ReplaceTextConfig(
    val pattern: String,
    val replacement: String,
    val replaceAll: Boolean = false,
)

val ReplaceText = ScenarioFactory.create<ReplaceTextConfig>("replace_text.yaml") { config ->
    when (config.replaceAll) {
        true -> ReplaceOutgoingTextMessage(config.pattern) {
            config.replacement.executeTemplate(request)
        }
        else -> ReplaceInOutgoingTextMessage(config.pattern) {
            config.replacement.executeTemplate(request)
        }
    }
}