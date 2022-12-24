package com.tdbot.scenario

import com.justai.jaicf.channel.td.Td
import com.justai.jaicf.channel.td.isIncoming
import com.justai.jaicf.channel.td.isOutgoing
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onNewMessage
import com.justai.jaicf.channel.td.scenario.onNewTextMessage
import com.justai.jaicf.channel.td.text
import com.tdbot.api.createBotClient
import com.tdbot.api.isBotChat
import com.tdbot.api.isNotBotChat
import it.tdlight.jni.TdApi

fun TranscribeMyVoiceNotesWithTranscriberBot(language: String) = TdScenario {
    val bot = createBotClient("@transcriber_bot") { it.start() }

    onNewTextMessage(".*choose a language.*", isIncoming, isBotChat(bot)) {
        if (request.input.contains(language)) {
            reactions.say("/$language")
        }
    }

    onNewMessage<TdApi.MessageVoiceNote>(isOutgoing, isNotBotChat(bot)) {
        bot.forwardMessage(request.update.message) { reply ->
            val text = reply.get().content as TdApi.MessageText
            if (text.text.text.length <= 1024) {
                reactions.editCaption(text.text.text)
            } else {
                reactions.reply(Td.text(text.text.text))
            }
        }
    }
}