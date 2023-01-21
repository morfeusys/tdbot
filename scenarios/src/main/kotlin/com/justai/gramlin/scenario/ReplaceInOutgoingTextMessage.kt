package com.justai.gramlin.scenario

import com.justai.jaicf.channel.td.TdMessageActionContext
import com.justai.jaicf.channel.td.activator.asPattern
import com.justai.jaicf.channel.td.isOutgoing
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onTextMessage
import org.intellij.lang.annotations.Language

fun ReplaceInOutgoingTextMessage(
    @Language("regexp") pattern: String,
    replacement: TdMessageActionContext.(MatchResult) -> String
) = TdScenario {
    val regex = pattern.asPattern.toRegex()

    onTextMessage(isOutgoing) {
        val text = request.input.replace(regex) { replacement(this, it) }
        if (text != request.input) {
            reactions.editText(text)
        }
    }
}