package com.tdbot.server.scenarios

import com.tdbot.api.TdMessageContentType
import com.tdbot.scenario.SearchChatPostsOnMessage
import com.tdbot.server.TdScenarioFactory
import com.tdbot.server.executeTemplate

data class ReplyFromChatConfig(
    val message: Message,
    val reply: Reply,
) {
    data class Message(val pattern: String)

    data class Reply(
        val from: String,
        val limit: Int = 100,
        val types: List<TdMessageContentType> = emptyList(),
        val random: Boolean = false
    )
}

val ReplyFromChat =
    TdScenarioFactory.create<ReplyFromChatConfig>("reply_from_chat.yaml") { config ->
        SearchChatPostsOnMessage(
            chat = config.reply.from.executeTemplate(),
            pattern = config.message.pattern.executeTemplate(),
            limit = config.reply.limit,
            types = config.reply.types.toTypedArray())
        { messages ->
            when (config.reply.random) {
                true -> messages.shuffled().firstOrNull()
                else -> messages.firstOrNull()
            }?.also(reactions::forwardMessage)
        }
    }