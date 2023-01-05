package com.tdbot.scenario

import com.justai.jaicf.channel.td.TdRegexMessageActionContext
import com.justai.jaicf.channel.td.isOutgoing
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onTextMessage
import org.intellij.lang.annotations.Language

fun ReplaceOutgoingTextMessage(
    @Language("regexp") pattern: String,
    replacement: TdRegexMessageActionContext.() -> String?
) = TdScenario {

    onTextMessage(pattern, isOutgoing) {
        replacement(this)?.also {
            if (it != request.input) {
                reactions.editText(it)
            }
        }
    }
}