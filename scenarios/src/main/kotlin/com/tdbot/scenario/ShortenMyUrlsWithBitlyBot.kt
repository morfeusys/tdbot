package com.tdbot.scenario

import com.justai.jaicf.channel.td.isOutgoing
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onTextMessage
import com.tdbot.api.createBotClient
import com.tdbot.api.isNotBotChat
import it.tdlight.jni.TdApi

fun ShortenMyUrlsWithBitlyBot(maxUrlLength: Int = 50) = TdScenario {
    val bitlyBot = createBotClient("@BitlyBot")

    onTextMessage(isOutgoing, isNotBotChat(bitlyBot)) {
        val text = request.content.text.text
        request.content.text.entities.forEach { e ->
            if (e.type is TdApi.TextEntityTypeUrl) {
                val url = text.substring(e.offset, e.offset + e.length)

                if (url.length > maxUrlLength) {
                    bitlyBot.sendInlineQuery(url)
                        .results
                        .filterIsInstance(TdApi.InlineQueryResultArticle::class.java)
                        .filter { it.id == "shortinl" }
                        .mapNotNull { it.title }
                        .firstOrNull()?.also { shorten ->
                            reactions.editText(text.replace(url, shorten))
                        }
                }
            }
        }
    }
}