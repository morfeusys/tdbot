package com.tdbot.scenario

import com.justai.jaicf.channel.td.TdRegexMessageActionContext
import com.justai.jaicf.channel.td.api.TdTelegramApi
import com.justai.jaicf.channel.td.isNotChat
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onTextMessage
import com.tdbot.api.TdMessageContentType
import com.tdbot.api.isInstance
import com.tdbot.api.searchAnyChat
import it.tdlight.jni.TdApi
import org.intellij.lang.annotations.Language

fun SearchChatPostsOnMessage(
    chat: String,
    @Language("regexp") pattern: String,
    limit: Int = 100,
    types: Array<TdMessageContentType> = emptyArray(),
    resultHandler: TdRegexMessageActionContext.(messages: List<TdApi.Message>) -> Unit
) = TdScenario {
    val chatId = searchAnyChat(chat)

    fun TdTelegramApi.searchPosts(query: String) =
        send(TdApi.SearchChatMessages(chatId.get()!!, query, null, 0, 0, limit, null, 0L))

    onTextMessage(pattern, isNotChat { chatId.get() }) {
        mutableListOf<TdApi.Message>().apply {
            when {
                activator.matcher.groupCount() <= 1 -> request.input
                else -> activator.matcher.groupCount().downTo(1).mapNotNull { activator.group(it) }.joinToString(" ")
            }.let { query ->
                addAll(reactions.api.searchPosts(query).messages)
            }
        }.also { result ->
            resultHandler(this@onTextMessage, result.filter { msg ->
                types.isEmpty() || types.any { it.isInstance(msg.content) }
            })
        }
    }
}