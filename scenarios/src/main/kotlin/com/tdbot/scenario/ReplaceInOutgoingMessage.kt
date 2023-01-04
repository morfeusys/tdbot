package com.tdbot.scenario

import com.justai.jaicf.channel.td.isOutgoing
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onTextMessage
import org.intellij.lang.annotations.Language

fun ReplaceInOutgoingMessage(
    @Language("regex") pattern: String,
    replacement: (MatchResult) -> String
) = TdScenario {
    onTextMessage(isOutgoing) {
        reactions.editText(request.input.replace(Regex(pattern, setOf(RegexOption.IGNORE_CASE, RegexOption.MULTILINE)), replacement))
    }
}