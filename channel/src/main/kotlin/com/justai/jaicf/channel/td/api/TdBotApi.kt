package com.justai.jaicf.channel.td.api

import com.justai.jaicf.channel.td.TdMessage
import com.justai.jaicf.channel.td.api.TdTelegramApi
import com.justai.jaicf.channel.td.api.parseText
import com.justai.jaicf.channel.td.api.sendMessage
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

    fun say(
        text: String,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = say(api.parseText(text, parseMode), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun say(
        text: TdApi.FormattedText,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.text(text), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun image(
        url: String,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = image(url, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun image(
        url: String,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.photo(url, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun image(
        file: File,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = image(file, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun image(
        file: File,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.photo(file.asLocalFile, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun animation(
        url: String,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = animation(url, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun animation(
        url: String,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.animation(url, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun animation(
        file: File,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = animation(file, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun animation(
        file: File,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.animation(file.asLocalFile, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun video(
        url: String,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = video(url, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun video(
        url: String,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.video(url, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun video(
        file: File,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = video(file, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun video(
        file: File,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.video(file.asLocalFile, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun audio(
        url: String,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = audio(url, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun audio(
        url: String,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.audio(url, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun audio(
        file: File,
        caption: String? = null,
        parseMode: TdMessage.ParseMode? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = audio(file, caption?.let { api.parseText(it, parseMode) }, options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun audio(
        file: File,
        caption: TdApi.FormattedText? = null,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.audio(file.asLocalFile, caption = caption), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun videoNote(
        url: String,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.videoNote(url), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun videoNote(
        file: File,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.videoNote(file.asLocalFile), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun voiceNote(
        url: String,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.voiceNote(url), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun voiceNote(
        file: File,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.voiceNote(file.asLocalFile), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sticker(
        url: String,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.sticker(url), options, replyMarkup, chatId, messageThreadId, replyToMessageId)

    fun sticker(
        file: File,
        options: TdApi.MessageSendOptions? = null,
        replyMarkup: TdApi.ReplyMarkup? = null,
        chatId: Long = this.chatId!!,
        messageThreadId: Long = 0,
        replyToMessageId: Long = 0
    ) = sendMessage(TdMessage.sticker(file.asLocalFile), options, replyMarkup, chatId, messageThreadId, replyToMessageId)
}