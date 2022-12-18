package com.tdbot.scenario

import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onNewTextMessage
import org.intellij.lang.annotations.Language

fun ReplyWithPhotoOnMessage(
    @Language("regexp") pattern: String,
    photoUrl: TdActionContext.() -> String?
) = TdScenario {

    onNewTextMessage(pattern) {
        photoUrl(this)?.let { url ->
            reactions.reply(Td.photo(url))
        }
    }
}