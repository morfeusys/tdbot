package com.justai.jaicf.channel.td

import com.justai.jaicf.channel.td.client.*
import com.justai.jaicf.logging.AudioReaction
import com.justai.jaicf.logging.ImageReaction
import com.justai.jaicf.logging.SayReaction
import com.justai.jaicf.reactions.Reactions
import it.tdlight.jni.TdApi

val Reactions.td get() = this as? TdReactions

class TdReactions(
    val api: TdTelegramApi,
    private val request: DefaultTdRequest,
): Reactions() {
    private val chatId = request.chatId

    private fun <R> withChatId(function: (chatId: Long) -> R): R? = chatId?.let { function(it) }

    fun sendContent(content: TdApi.InputMessageContent) =
        withChatId { chatId ->
            api.sendMessage(chatId, content = content)
        }

    fun reply(content: TdApi.InputMessageContent) =
        withChatId { chatId ->
            request.messageId?.let { messageId ->
                api.sendMessage(chatId, replyToMessageId = messageId, content = content)
            }
        }

    fun editText(text: String) = editText(Td.text(text))

    fun editText(text: TdApi.InputMessageText) = withChatId { chatId ->
        request.messageId?.let { messageId ->
            api.editMessageText(chatId, messageId, content = text)
        }
    }

    fun editCaption(text: String, entities: Array<out TdApi.TextEntity>? = null) = withChatId { chatId ->
        request.messageId?.let { messageId ->
            api.editMessageCaption(chatId, messageId, caption = TdApi.FormattedText(text, entities))
        }
    }

    override fun say(text: String) = SayReaction.create(text).also {
        sendContent(Td.text(text))
    }

    override fun image(url: String) = ImageReaction.create(url).also {
        sendContent(Td.photo(url))
    }

    override fun audio(url: String) = AudioReaction.create(url).also {
        sendContent(Td.audio(url))
    }

    fun forward(message: TdApi.Message, chat: Long? = chatId) =
        forward(arrayOf(message), chat)

    fun forward(messages: Array<TdApi.Message>, chat: Long? = chatId) {
        if (chat != null)
            api.forwardMessages(chat, fromChatId = messages.first().chatId, messageIds = messages.map { it.id }.toTypedArray())
    }

    fun forward(chat: Long) {
        request.message?.let { message ->
            if (message.update.message.canBeForwarded) {
                api.forwardMessages(chat, fromChatId = message.chatId!!, messageIds = arrayOf(message.messageId!!))
            }
        }
    }

    fun deleteMessages(messages: LongArray, revoke: Boolean = false) =
        withChatId { chatId ->
            api.send(TdApi.DeleteMessages(chatId, messages, revoke))
        }
}
