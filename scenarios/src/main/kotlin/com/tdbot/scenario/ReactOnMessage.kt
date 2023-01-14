package com.tdbot.scenario

import com.justai.jaicf.channel.td.TdMessageActionContext
import com.justai.jaicf.channel.td.isIncoming
import com.justai.jaicf.channel.td.isNotMyMessage
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onAnyMessage
import com.justai.jaicf.channel.td.text
import com.tdbot.scenario.utils.multilineCaseInsensitiveRegex
import org.intellij.lang.annotations.Language
import java.util.regex.Matcher

fun ReactOnMessage(
    @Language("regexp") pattern: String,
    handler: TdMessageActionContext.(Matcher) -> Unit
) = TdScenario {
    val regex = pattern.multilineCaseInsensitiveRegex.toPattern()

    onAnyMessage(isIncoming, isNotMyMessage) {
        request.content.text?.let { text ->
            regex.matcher(text.text).takeIf { it.matches() }?.also {
                handler.invoke(this, it)
            }
        }
    }
}