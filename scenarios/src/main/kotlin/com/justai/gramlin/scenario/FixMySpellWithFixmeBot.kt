package com.justai.gramlin.scenario

import com.justai.jaicf.channel.td.isOutgoing
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onTextMessage
import com.justai.gramlin.api.createBotClient
import com.justai.gramlin.api.isNotBotChat
import it.tdlight.jni.TdApi

val FixMySpellWithFixmeBot = TdScenario {
    val fixmeBot = createBotClient("@fixmebot")

    onTextMessage(isOutgoing, isNotBotChat(fixmeBot)) {
        fixmeBot.sendInlineQuery(request.input)
            .results
            .filterIsInstance(TdApi.InlineQueryResultArticle::class.java)
            .map { it.description }
            .firstOrNull { it != request.input }
            ?.let { result -> reactions.editText(result) }
    }
}