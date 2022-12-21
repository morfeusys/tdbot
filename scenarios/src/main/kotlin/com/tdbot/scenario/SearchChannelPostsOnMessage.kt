package com.tdbot.scenario

import com.justai.jaicf.channel.td.TdActionContext
import com.justai.jaicf.channel.td.client.TdTelegramApi
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.channel.td.isNotChat
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onNewTextMessage
import it.tdlight.jni.TdApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Language
import org.slf4j.LoggerFactory
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

fun SearchChannelPostsOnMessage(
    chat: String,
    @Language("regexp") pattern: String,
    limit: Int = 100,
    resultHandler: TdActionContext.(messages: List<TdApi.Message>) -> Unit
) = TdScenario {
    val logger = LoggerFactory.getLogger("SearchChannelPostsOnMessage_$chat")
    var chatId: Long? = null

    handle<TdReadyHook> {
        api.send(TdApi.SearchPublicChat("@cats_cats")) { res ->
            if (res.isError || res.get() == null) {
                logger.error("Cannot find @cats_cats")
            } else {
                chatId = res.get().id
            }
        }
    }

    suspend fun TdTelegramApi.searchPosts(query: String) =
        suspendCoroutine<List<TdApi.Message>> { continuation ->
            send(TdApi.SearchChatMessages(chatId!!, query, null, 0, 0, limit, null, 0L)) { res ->
                continuation.resume(res.get().messages.toList())
            }
        }

    onNewTextMessage(pattern, isNotChat { chatId }) {
        GlobalScope.launch {
            mutableListOf<TdApi.Message>().apply {
                when {
                    activator.matcher.groupCount() == 1 -> request.input
                    else -> activator.matcher.groupCount().downTo(1).mapNotNull { activator.group(it) }.joinToString(" ")
                }.let { query ->
                    addAll(reactions.api.searchPosts(query))
                }
            }.also { result ->
                resultHandler(this@onNewTextMessage, result)
            }
        }
    }
}