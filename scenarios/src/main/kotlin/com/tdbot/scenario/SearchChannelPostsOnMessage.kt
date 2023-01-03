package com.tdbot.scenario

import com.justai.jaicf.channel.td.TdActionContext
import com.justai.jaicf.channel.td.client.TdTelegramApi
import com.justai.jaicf.channel.td.isNotChat
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onReady
import com.justai.jaicf.channel.td.scenario.onTextMessage
import it.tdlight.jni.TdApi
import org.intellij.lang.annotations.Language

fun SearchChannelPostsOnMessage(
    chat: String,
    @Language("regexp") pattern: String,
    limit: Int = 100,
    resultHandler: TdActionContext.(messages: List<TdApi.Message>) -> Unit
) = TdScenario {
    var chatId: Long? = null

    onReady {
        chatId = api.send(TdApi.SearchPublicChat(chat)).id
    }

    fun TdTelegramApi.searchPosts(query: String) =
        send(TdApi.SearchChatMessages(chatId!!, query, null, 0, 0, limit, null, 0L))

    onTextMessage(pattern, isNotChat { chatId }) {
        mutableListOf<TdApi.Message>().apply {
            when {
                activator.matcher.groupCount() == 1 -> request.input
                else -> activator.matcher.groupCount().downTo(1).mapNotNull { activator.group(it) }.joinToString(" ")
            }.let { query ->
                addAll(reactions.api.searchPosts(query).messages)
            }
        }.also { result ->
            resultHandler(this@onTextMessage, result)
        }
    }
}