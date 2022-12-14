package com.justai.jaicf.channel.td

import com.justai.jaicf.channel.td.api.*
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
        text: String,
        options: TdApi.MessageSendOptions? = null,
        messageThreadId: Long = 0) = reply(TdMessage.text(text), options, messageThreadId)

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
        editText(TdMessage.text(text, entities))

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
        sendMessage(TdMessage.text(text))
    }

    fun say(text: String, entities: Array<out TdApi.TextEntity>) =
        sendMessage(TdMessage.text(text, entities))

    override fun image(url: String) = ImageReaction.create(url).also {
        sendMessage(TdMessage.photo(url))
    }

    fun animation(url: String) = ImageReaction.create(url).also {
        sendMessage(TdMessage.animation(url))
    }

    override fun audio(url: String) = AudioReaction.create(url).also {
        sendMessage(TdMessage.audio(url))
    }

    fun video(url: String) = VideoReaction.create(url).also {
        sendMessage(TdMessage.video(url))
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
        api.send(TdApi.DeleteMessages(chatId!!, longArrayOf(mr.messageId!!), revoke))
    }

    fun addEmojiReaction(reaction: TdApi.ReactionTypeEmoji, isBig: Boolean = false, updateRecentReactions: Boolean = false) =
        request.messageRequest?.let { mr ->
            api.addMessageReaction(chatId!!, mr.messageId!!, reaction, isBig, updateRecentReactions)
        }

    fun clickAnimatedEmoji() = request.messageRequest?.let { mr ->
        api.send(TdApi.ClickAnimatedEmojiMessage(chatId!!, mr.messageId!!))
    }
}
