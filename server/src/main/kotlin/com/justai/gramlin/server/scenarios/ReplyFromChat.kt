package com.justai.gramlin.server.scenarios

import com.justai.gramlin.api.TdMessageContentType
import com.justai.gramlin.scenario.SearchChatPostsOnMessage
import com.justai.gramlin.server.ScenarioFactory
import com.justai.gramlin.server.executeTemplate

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
    ScenarioFactory.create<ReplyFromChatConfig>("reply_from_chat.yaml") { config ->
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