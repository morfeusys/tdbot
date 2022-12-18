package com.tdbot.scenario

import com.justai.jaicf.channel.td.isOutgoing
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onAnyNewTextMessage
import com.tdbot.api.createBotClient
import com.tdbot.api.isNotBotChat
import it.tdlight.jni.TdApi

val GrammarChecker = TdScenario {
    val bot = createBotClient("@fixmebot")

    onAnyNewTextMessage(isOutgoing, isNotBotChat(bot)) {
        bot.sendInlineQuery(request.input) { res ->
            if (!res.isError) {
                res.get().results
                    .filterIsInstance(TdApi.InlineQueryResultArticle::class.java)
                    .map { it.description }
                    .firstOrNull { it != request.input }
                    ?.let { result -> reactions.editText(result) }
            }
        }
    }
}