package com.justai.jaicf.channel.td

import com.justai.jaicf.channel.td.client.*
import com.justai.jaicf.logging.AudioReaction
import com.justai.jaicf.logging.ImageReaction
import com.justai.jaicf.logging.SayReaction
import com.justai.jaicf.logging.VideoReaction
import com.justai.jaicf.reactions.Reactions
import it.tdlight.jni.TdApi

val Reactions.td get() = this as? TdReactions

class TdReactions(
    val api: TdTelegramApi,
    private val request: DefaultTdRequest,
): Reactions() {
    private val chatId = request.chatId

    private fun <R> withChatId(function: (chatId: Long) -> R): R? = chatId?.let { function(it) }

    fun sendMessage(
        content: TdApi.InputMessageContent,
        options: TdApi.MessageSendOptions? = null,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = withChatId { chatId ->
        api.sendMessage(chatId, messageThreadId, replyToMessageId, options, content = content)
    }

    fun reply(
        content: TdApi.InputMessageContent,
        options: TdApi.MessageSendOptions? = null,
        messageThreadId: Long = 0
    ) = withChatId { chatId ->
        request.messageId?.let { messageId ->
            api.sendMessage(chatId, messageThreadId, messageId, options, content = content)
        }
    }

    fun editText(text: String, entities: Array<out TdApi.TextEntity> = emptyArray()) =
        editText(Td.text(text, entities))

    fun editText(text: TdApi.InputMessageText) = withChatId { chatId ->
        request.messageId?.let { messageId ->
            api.editMessageText(chatId, messageId, content = text)
        }
    }

    fun editCaption(text: String, entities: Array<out TdApi.TextEntity> = emptyArray()) = withChatId { chatId ->
        request.messageId?.let { messageId ->
            api.editMessageCaption(chatId, messageId, caption = TdApi.FormattedText(text, entities))
        }
    }

    override fun say(text: String) = SayReaction.create(text).also {
        sendMessage(Td.text(text))
    }

    fun say(text: String, entities: Array<out TdApi.TextEntity>) =
        sendMessage(Td.text(text, entities))

    override fun image(url: String) = ImageReaction.create(url).also {
        sendMessage(Td.photo(url))
    }

    fun animation(url: String) = ImageReaction.create(url).also {
        sendMessage(Td.animation(url))
    }

    override fun audio(url: String) = AudioReaction.create(url).also {
        sendMessage(Td.audio(url))
    }

    fun video(url: String) = VideoReaction.create(url).also {
        sendMessage(Td.video(url))
    }

    fun forward(message: TdApi.Message) = withChatId { chatId ->
        api.forwardMessages(chatId, fromChatId = message.chatId, messageIds = arrayOf(message.id))
    }

    fun forward(toChatId: Long) = request.messageRequest?.let { mr ->
        if (mr.update.message.canBeForwarded) {
            api.forwardMessages(toChatId, fromChatId = request.chatId!!, messageIds = arrayOf(request.messageId!!))
        }
    }

    fun delete(revoke: Boolean = false) = request.messageRequest?.let { mr ->
        api.send(TdApi.DeleteMessages(mr.chatId!!, longArrayOf(mr.messageId!!), revoke))
    }
}
