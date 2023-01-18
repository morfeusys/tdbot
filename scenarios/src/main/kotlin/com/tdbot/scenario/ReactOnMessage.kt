package com.tdbot.scenario

import com.justai.jaicf.channel.td.TdMessageActionContext
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onAnyMessage
import com.tdbot.api.TdMessageContentType
import com.tdbot.api.condition
import org.intellij.lang.annotations.Language
import java.util.regex.Matcher

fun ReactOnMessage(
    @Language("regexp") pattern: String,
    types: Array<TdMessageContentType> = emptyArray(),
    handler: TdMessageActionContext.(Matcher) -> Unit
) = TdScenario {
    onAnyMessage(pattern, conditions = types.map { it.condition }.toTypedArray()) {
        handler.invoke(this, it)
    }
}