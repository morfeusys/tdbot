package com.tdbot.scenario

import com.justai.jaicf.channel.td.*

fun SendPhotoOnMyMessage(
    pattern: String,
    photoUrl: TdActionContext.() -> String?
) = TdScenario {

    onNewTextMessage(pattern, isOutgoing, isDirect) {
        photoUrl(this)?.let { url ->
            reactions.deleteMessages(longArrayOf(request.messageId!!), true) {
                reactions.image(url)
            }
        }
    }
}