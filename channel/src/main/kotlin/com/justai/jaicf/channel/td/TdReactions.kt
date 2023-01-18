package com.justai.jaicf.channel.td

import com.justai.jaicf.channel.td.api.*
import com.justai.jaicf.channel.td.request.*
import com.justai.jaicf.logging.AudioReaction
import com.justai.jaicf.logging.ButtonsReaction
import com.justai.jaicf.logging.ImageReaction
import com.justai.jaicf.logging.SayReaction
import com.justai.jaicf.reactions.Reactions
import it.tdlight.jni.TdApi

val Reactions.td get() = this as? TdReactions

class TdReactions(
    override val api: TdTelegramApi,
    private val request: DefaultTdRequest,
): Reactions(), TdBotApi {
    override val chatId = request.chatId

    override fun say(text: String) = SayReaction.create(text).also { sendText(text) }

    override fun image(url: String) = ImageReaction.create(url).also { sendPhoto(url) }

    override fun audio(url: String) = AudioReaction.create(url).also { sendAudio(url) }

    override fun buttons(vararg buttons: String): ButtonsReaction {
        api.awaitLastMessage()?.let { message ->
            val texts = buttons.map { text ->
                var s = text.take(21)
                if (s.length < text.length) {
                    s = s.replaceRange(18..20, "...")
                }
                s to text
            }.toMap()
            val keyboard = (message.replyMarkup as? TdApi.ReplyMarkupInlineKeyboard)?.rows?.toMutableList() ?: mutableListOf<Array<TdApi.InlineKeyboardButton>>()
            keyboard.addAll(texts.map { (text, value) ->
                arrayOf(TdApi.InlineKeyboardButton(text, TdApi.InlineKeyboardButtonTypeCallback(value.toByteArray())))
            })
            val replyMarkup = TdApi.ReplyMarkupInlineKeyboard(keyboard.toTypedArray())
            api.editMessageReplyMarkup(message.chatId, message.id, replyMarkup)
        }
        return ButtonsReaction.create(buttons.asList())
    }

    fun reply(
        text: String,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        messageThreadId: Long = 0) = reply(TdMessage.text(api.parseText(text, parseMode)), options, replyMarkup, messageThreadId)

    fun reply(
        content: TdApi.InputMessageContent,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        messageThreadId: Long = 0
    ) = sendMessage(content, options, replyMarkup, messageThreadId = messageThreadId, replyToMessageId = request.messageId!!)

    fun editText(text: String, parseMode: TdMessage.ParseMode? = null, replyMarkup: TdApi.ReplyMarkup? = null) =
        editText(TdMessage.text(api.parseText(text, parseMode)), replyMarkup)

    fun editText(text: TdApi.InputMessageText, replyMarkup: TdApi.ReplyMarkup? = null) =
        api.editMessageText(chatId!!, request.messageId!!, replyMarkup, text)

    fun editCaption(text: String, parseMode: TdMessage.ParseMode? = null, replyMarkup: TdApi.ReplyMarkup? = null) =
        editCaption(api.parseText(text, parseMode), replyMarkup)

    fun editCaption(text: TdApi.FormattedText, replyMarkup: TdApi.ReplyMarkup? = null) =
        api.editMessageCaption(chatId!!, request.messageId!!, replyMarkup, text)

    fun forwardMessageToChat(toChatId: Long) =
        forwardMessage(request.messageRequest!!.update.message, toChatId)

    fun deleteMessage(revoke: Boolean = false) =
        api.send(TdApi.DeleteMessages(chatId!!, longArrayOf(request.messageId!!), revoke))

    fun addEmojiReaction(reaction: TdApi.ReactionTypeEmoji, isBig: Boolean = false, updateRecentReactions: Boolean = false) =
        api.addMessageReaction(chatId!!, request.messageId!!, reaction, isBig, updateRecentReactions)

    fun clickAnimatedEmoji() =
        api.send(TdApi.ClickAnimatedEmojiMessage(chatId!!, request.messageId!!))
}
