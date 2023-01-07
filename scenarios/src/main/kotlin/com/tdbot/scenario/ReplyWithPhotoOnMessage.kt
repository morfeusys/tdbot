package com.tdbot.scenario

import com.justai.jaicf.channel.td.TdMessage
import com.justai.jaicf.channel.td.TdRegexMessageActionContext
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onTextMessage
import org.intellij.lang.annotations.Language

fun ReplyWithPhotoOnMessage(
    @Language("regexp") pattern: String,
    photoUrl: TdRegexMessageActionContext.() -> String?
) = TdScenario {

    onTextMessage(pattern) {
        photoUrl(this)?.let { url ->
            reactions.reply(TdMessage.photo(url))
        }
    }
}