package com.tdbot.scenario

import com.justai.jaicf.channel.td.isOutgoing
import com.justai.jaicf.channel.td.onAnyNewTextMessage
import com.justai.jaicf.channel.td.onNewTextMessage
import com.tdbot.api.TdBotClientScenario
import com.tdbot.api.isNotBotChat
import it.tdlight.jni.TdApi

val GrammarChecker = TdBotClientScenario("@fixmebot") { bot ->

    onAnyNewTextMessage(isOutgoing, isNotBotChat(bot)) { _, _ ->
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