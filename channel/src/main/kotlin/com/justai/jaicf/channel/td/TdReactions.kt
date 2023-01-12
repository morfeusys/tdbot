package com.justai.jaicf.channel.td

import com.justai.jaicf.channel.td.api.*
import com.justai.jaicf.channel.td.request.DefaultTdRequest
import com.justai.jaicf.channel.td.request.chatId
import com.justai.jaicf.channel.td.request.messageId
import com.justai.jaicf.channel.td.request.messageRequest
import com.justai.jaicf.logging.*
import com.justai.jaicf.reactions.Reactions
import it.tdlight.jni.TdApi
import java.util.concurrent.TimeUnit

val Reactions.td get() = this as? TdReactions

class TdReactions(
    val api: TdTelegramApi,
    private val request: DefaultTdRequest,
): Reactions() {
    private val chatId = request.chatId

    private fun <R> withChatId(function: (chatId: Long) -> R): R? = chatId?.let { function(it) }

    private fun parse(text: String, parseMode: TdMessage.ParseMode) =
        api.send(TdApi.ParseTextEntities(text, TdApi.TextParseModeMarkdown(parseMode.ordinal)))

    fun sendMessage(
        content: TdApi.InputMessageContent,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = withChatId { chatId ->
        api.sendMessage(chatId, messageThreadId, replyToMessageId, options, replyMarkup, content)
    }

    override fun buttons(vararg buttons: String): ButtonsReaction {
        api.awaitLastMessage()?.let { message ->
            val keyboard = (message.replyMarkup as? TdApi.ReplyMarkupInlineKeyboard)?.rows?.toMutableList() ?: mutableListOf<Array<TdApi.InlineKeyboardButton>>()
            keyboard.addAll(buttons.map { text ->
                arrayOf(TdApi.InlineKeyboardButton(text, TdApi.InlineKeyboardButtonTypeCallback(text.toByteArray())))
            })
            val replyMarkup = TdApi.ReplyMarkupInlineKeyboard(keyboard.toTypedArray())
            api.editMessageReplyMarkup(message.chatId, message.id, replyMarkup)
        }
        return ButtonsReaction.create(buttons.asList())
    }

    fun reply(
        text: String,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        messageThreadId: Long = 0) = reply(TdMessage.text(text), options, replyMarkup, messageThreadId)

    fun reply(
        content: TdApi.InputMessageContent,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        messageThreadId: Long = 0
    ) = withChatId { chatId ->
        request.messageId?.let { messageId ->
            api.sendMessage(chatId, messageThreadId, messageId, options, replyMarkup, content)
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

    fun say(
        text: String,
        entities: Array<out TdApi.TextEntity> = emptyArray(),
        replyMarkup: TdApi.ReplyMarkup? = null
    ) = sendMessage(TdMessage.text(text, entities), replyMarkup = replyMarkup)

    fun say(
        text: TdApi.FormattedText,
        replyMarkup: TdApi.ReplyMarkup? = null
    ) = sendMessage(TdMessage.text(text), replyMarkup = replyMarkup)

    fun say(text: String, parseMode: TdMessage.ParseMode, replyMarkup: TdApi.ReplyMarkup? = null) =
        say(parse(text, parseMode), replyMarkup)

    fun sayMarkdown(text: String, replyMarkup: TdApi.ReplyMarkup? = null) =
        say(text, TdMessage.ParseMode.Markdown, replyMarkup)

    override fun image(url: String) = ImageReaction.create(url).also {
        sendMessage(TdMessage.photo(url))
    }

    fun image(url: String, caption: String) =
        sendMessage(TdMessage.photo(url, caption = TdApi.FormattedText(caption, null)))

    fun image(url: String, caption: String, parseMode: TdMessage.ParseMode) =
        sendMessage(TdMessage.photo(url, caption = parse(caption, parseMode)))

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
