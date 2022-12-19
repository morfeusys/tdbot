package com.justai.jaicf.channel.td

import com.justai.jaicf.channel.td.client.*
import com.justai.jaicf.logging.AudioReaction
import com.justai.jaicf.logging.ImageReaction
import com.justai.jaicf.logging.SayReaction
import com.justai.jaicf.reactions.Reactions
import it.tdlight.client.GenericResultHandler
import it.tdlight.jni.TdApi

val Reactions.td get() = this as? TdReactions

class TdReactions(
    val api: TdTelegramApi,
    private val request: DefaultTdRequest,
): Reactions() {
    private val chatId = request.chatId

    private fun <R> withChatId(function: (chatId: Long) -> R): R? = chatId?.let { function(it) }

    fun sendContent(content: TdApi.InputMessageContent, handler: GenericResultHandler<TdApi.Message> = DefaultResultHandler()) =
        withChatId { chatId ->
            api.sendMessage(chatId, content = content, handler = handler)
        }

    fun reply(content: TdApi.InputMessageContent, handler: GenericResultHandler<TdApi.Message> = DefaultResultHandler()) =
        withChatId { chatId ->
            request.messageId?.let { messageId ->
                api.sendMessage(chatId, replyToMessageId = messageId, content = content, handler = handler)
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

    override fun say(text: String) = say(text, DefaultResultHandler())

    fun say(text: String, handler: GenericResultHandler<TdApi.Message>) = SayReaction.create(text).also {
        sendContent(Td.text(text), handler)
    }

    override fun image(url: String) = image(url, DefaultResultHandler())

    fun image(url: String, handler: GenericResultHandler<TdApi.Message>) = ImageReaction.create(url).also {
        sendContent(Td.photo(url), handler)
    }

    override fun audio(url: String) = audio(url, DefaultResultHandler())

    fun audio(url: String, handler: GenericResultHandler<TdApi.Message>) = AudioReaction.create(url).also {
        sendContent(Td.audio(url), handler)
    }

    fun forward(messages: Array<TdApi.Message>, chat: Long? = chatId, handler: GenericResultHandler<TdApi.Messages> = DefaultResultHandler()) {
        if (chat != null)
            api.forwardMessages(chat, fromChatId = messages.first().chatId, messageIds = messages.map { it.id }.toTypedArray(), handler = handler)
    }

    fun forward(chat: Long, handler: GenericResultHandler<TdApi.Messages> = DefaultResultHandler()) {
        request.message?.let { message ->
            if (message.update.message.canBeForwarded) {
                api.forwardMessages(chat, fromChatId = message.chatId!!, messageIds = arrayOf(message.messageId!!), handler = handler)
            }
        }
    }

    fun deleteMessages(messages: LongArray, revoke: Boolean = false, handler: GenericResultHandler<TdApi.Ok> = DefaultResultHandler()) =
        withChatId { chatId ->
            api.send(TdApi.DeleteMessages(chatId, messages, revoke), handler)
        }
}
