package com.justai.gramlin.scenario

import com.justai.gramlin.api.TdMessageContentType
import com.justai.gramlin.api.condition
import com.justai.jaicf.channel.td.TdMessageActionContext
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onAnyMessage
import org.intellij.lang.annotations.Language

fun ReactOnMessage(
    @Language("regexp") pattern: String,
    types: Array<TdMessageContentType> = emptyArray(),
    handler: TdMessageActionContext.() -> Unit
) = TdScenario {
    onAnyMessage(pattern, conditions = types.map { it.condition }.toTypedArray()) {
        handler.invoke(this)
    }
}