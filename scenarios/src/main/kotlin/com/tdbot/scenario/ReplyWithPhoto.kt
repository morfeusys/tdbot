package com.tdbot.scenario

import com.justai.jaicf.channel.td.*

fun ReplyWithPhoto(
    pattern: String,
    photoUrl: TdActionContext.() -> String?
) = TdScenario {

    onNewTextMessage(pattern, isIncoming, isDirect) {
        photoUrl(this)?.let { url ->
            reactions.reply(Td.photo(url))
        }
    }
}