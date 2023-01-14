package com.justai.jaicf.channel.td.api

import com.justai.jaicf.channel.td.TdMessage
import it.tdlight.jni.TdApi
import java.io.File

val File.asLocalFile
    get() = TdApi.InputFileLocal(absolutePath)

interface TdBotApi {
    val chatId: Long?
    val api: TdTelegramApi

    private fun TdApi.Message.await() = api.awaitMessage(id) ?: this

    fun sendEvent(event: String, data: String = "") {}

    fun sendMessage(
        content: TdApi.InputMessageContent,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = api.sendMessage(chatId, messageThreadId, replyToMessageId, options, replyMarkup, content).await()

    fun sendText(
        text: String,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendText(api.parseText(text, parseMode), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendText(
        text: TdApi.FormattedText,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.text(text), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendPhoto(
        url: String,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendPhoto(url, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendPhoto(
        url: String,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.photo(url, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendPhoto(
        file: File,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendPhoto(file, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendPhoto(
        file: File,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.photo(file.asLocalFile, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendAnimation(
        url: String,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendAnimation(url, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendAnimation(
        url: String,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.animation(url, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendAnimation(
        file: File,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendAnimation(file, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendAnimation(
        file: File,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.animation(file.asLocalFile, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendVideo(
        url: String,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendVideo(url, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendVideo(
        url: String,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.video(url, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendVideo(
        file: File,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendVideo(file, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendVideo(
        file: File,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.video(file.asLocalFile, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendAudio(
        url: String,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendAudio(url, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendAudio(
        url: String,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.audio(url, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendAudio(
        file: File,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendAudio(file, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendAudio(
        file: File,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.audio(file.asLocalFile, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendVideoNote(
        url: String,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.videoNote(url), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendVideoNote(
        file: File,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.videoNote(file.asLocalFile), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendVoiceNote(
        url: String,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.voiceNote(url), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendVoiceNote(
        file: File,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.voiceNote(file.asLocalFile), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendSticker(
        url: String,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.sticker(url), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sendSticker(
        file: File,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.sticker(file.asLocalFile), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun forwardMessage(
        message: TdApi.Message,
        chatId: Long = this.chatId!!
    ) = api.forwardMessages(chatId, fromChatId = message.chatId, messageIds = arrayOf(message.id))
}