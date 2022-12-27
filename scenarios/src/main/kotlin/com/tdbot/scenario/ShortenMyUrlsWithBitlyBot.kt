package com.tdbot.scenario

import com.justai.jaicf.channel.td.isOutgoing
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onAnyTextMessage
import com.tdbot.api.createBotClient
import com.tdbot.api.isNotBotChat
import it.tdlight.jni.TdApi

fun ShortenMyUrlsWithBitlyBot(maxUrlLength: Int = 50) = TdScenario {
    val bot = createBotClient("@BitlyBot")

    onAnyTextMessage(isOutgoing, isNotBotChat(bot)) {
        val text = request.content.text.text
        request.content.text.entities.forEach { e ->
            if (e.type is TdApi.TextEntityTypeUrl) {
                val url = text.substring(e.offset, e.offset + e.length)

                if (url.length > maxUrlLength) {
                    bot.sendInlineQuery(url) { res ->
                        if (!res.isError) {
                            res.get().results
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
    }
}