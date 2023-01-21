package com.justai.gramlin.scenario

import com.justai.jaicf.channel.td.TdMessage
import com.justai.jaicf.channel.td.isOutgoing
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onVoiceNoteMessage
import com.justai.gramlin.api.createBotClient
import it.tdlight.jni.TdApi

fun TranscribeMyVoiceNotesWithTranscriberBot(language: String) = TdScenario {
    val transcriberBot = createBotClient("@transcriber_bot") {
        it.sendMessage("/$language")
    }

    onVoiceNoteMessage(isOutgoing) {
        transcriberBot.forwardMessage(request.update.message) { reply ->
            val text = reply.content as TdApi.MessageText
            if (text.text.text.length <= 1024) {
                reactions.editCaption(text.text)
            } else {
                reactions.reply(TdMessage.text(text))
            }
        }
    }
}