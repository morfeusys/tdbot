package com.tdbot.scenario

import com.justai.jaicf.channel.td.TdMessage
import com.justai.jaicf.channel.td.isOutgoing
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onVoiceNoteMessage
import com.tdbot.api.createBotClient
import com.tdbot.api.isNotBotChat
import it.tdlight.jni.TdApi

fun TranscribeMyVoiceNotesWithTranscriberBot(language: String) = TdScenario {
    val transcriberBot = createBotClient("@transcriber_bot") {
        it.sendMessage("/$language")
    }

    onVoiceNoteMessage(isOutgoing, isNotBotChat(transcriberBot)) {
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