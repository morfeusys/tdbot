package com.tdbot.scenario

import com.justai.jaicf.channel.td.TdMessageActionContext
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onAnyMessage
import org.intellij.lang.annotations.Language
import java.util.regex.Matcher

fun ReactOnMessage(
    @Language("regexp") pattern: String,
    handler: TdMessageActionContext.(Matcher) -> Unit
) = TdScenario {
    onAnyMessage(pattern) {
        handler.invoke(this, it)
    }
}