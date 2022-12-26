package com.tdbot.api

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.*
import com.github.kotlintelegrambot.entities.inputmedia.MediaGroup
import com.github.kotlintelegrambot.entities.polls.PollType
import java.io.File

interface TdBotApi {
    val chatId: ChatId?
    val telegram: Bot

    private fun <R> withChatId(function: (chatId: ChatId) -> R): R? = chatId?.let { function(it) }

    fun sendEvent(event: String, data: String = "")

    fun sendMessage(
        text: String,
        parseMode: ParseMode? = null,
        entities: List<MessageEntity>? = null,
        disableWebPagePreview: Boolean? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendMessage(it, text, parseMode, entities, disableWebPagePreview, disableNotification, replyToMessageId)
    }

    fun forwardMessage(
        fromChatId: ChatId,
        messageId: Long,
        disableNotification: Boolean? = null
    ) = withChatId {
        telegram.forwardMessage(it, fromChatId, messageId, disableNotification)
    }

    fun copyMessage(
        fromChatId: ChatId,
        messageId: Long,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        allowSendingWithoutReply: Boolean? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.copyMessage(it, fromChatId, messageId, caption, parseMode, captionEntities, disableNotification, replyToMessageId, allowSendingWithoutReply, replyMarkup)
    }

    fun sendPhoto(
        photo: File,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendPhoto(it, photo, caption, parseMode, captionEntities, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendPhoto(
        photo: String,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendPhoto(it, photo, caption, parseMode, captionEntities, disableNotification, replyToMessageId)
    }

    fun sendAudio(
        audio: String,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        duration: Int? = null,
        performer: String? = null,
        title: String? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendAudio(it, audio, caption, parseMode, captionEntities, duration, performer, title, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendAudio(
        audio: File,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        duration: Int? = null,
        performer: String? = null,
        title: String? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendAudio(it, audio, caption, parseMode, captionEntities, duration, performer, title, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendDocument(
        document: File,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendDocument(it, document, caption, parseMode, captionEntities, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendDocument(
        fileBytes: ByteArray,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null,
        fileName: String
    ) = withChatId {
        telegram.sendDocument(it, fileBytes, caption, parseMode, captionEntities, disableNotification, replyToMessageId, replyMarkup, fileName)
    }

    fun sendDocument(
        fileId: String,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendDocument(it, fileId, caption, parseMode, captionEntities, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendVideo(
        video: File,
        duration: Int? = null,
        width: Int? = null,
        height: Int? = null,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendVideo(it, video, duration, width, height, caption, parseMode, captionEntities, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendVideo(
        fileId: String,
        duration: Int? = null,
        width: Int? = null,
        height: Int? = null,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendVideo(it, fileId, duration, width, height, caption, parseMode, captionEntities, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendAnimation(
        animation: File,
        duration: Int? = null,
        width: Int? = null,
        height: Int? = null,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendAnimation(it, animation, duration, width, height, caption, parseMode, captionEntities, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendAnimation(
        fileId: String,
        duration: Int? = null,
        width: Int? = null,
        height: Int? = null,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendAnimation(it, fileId, duration, width, height, caption, parseMode, captionEntities, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendVoice(
        audio: ByteArray,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        duration: Int? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendVoice(it, audio, caption, parseMode, captionEntities, duration, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendVoice(
        audio: File,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        duration: Int? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendVoice(it, audio, caption, parseMode, captionEntities, duration, disableNotification, replyToMessageId)
    }

    fun sendVoice(
        audioId: String,
        caption: String? = null,
        parseMode: ParseMode? = null,
        captionEntities: List<MessageEntity>? = null,
        duration: Int? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendVoice(it, audioId, caption, parseMode, captionEntities, duration, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendVideoNote(
        videoNote: File,
        duration: Int? = null,
        length: Int? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendVideoNote(it, videoNote, duration, length, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendVideoNote(
        videoNoteId: String,
        duration: Int? = null,
        length: Int? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendVideoNote(it, videoNoteId, duration, length, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendMediaGroup(
        mediaGroup: MediaGroup,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null
    ) = withChatId {
        telegram.sendMediaGroup(it, mediaGroup, disableNotification, replyToMessageId)
    }

    fun sendLocation(
        latitude: Float,
        longitude: Float,
        livePeriod: Int? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendLocation(it, latitude, longitude, livePeriod, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendPoll(
        question: String,
        options: List<String>,
        isAnonymous: Boolean? = null,
        type: PollType? = null,
        allowsMultipleAnswers: Boolean? = null,
        correctOptionId: Int? = null,
        explanation: String? = null,
        explanationParseMode: ParseMode? = null,
        openPeriod: Int? = null,
        closeDate: Long? = null,
        isClosed: Boolean? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendPoll(it, question, options, isAnonymous, type, allowsMultipleAnswers, correctOptionId, explanation, explanationParseMode, openPeriod, closeDate, isClosed, disableNotification, replyToMessageId, replyMarkup)
    }

    fun editMessageLiveLocation(
        chatId: ChatId? = this.chatId,
        messageId: Long? = null,
        inlineMessageId: String? = null,
        latitude: Float,
        longitude: Float,
        replyMarkup: ReplyMarkup? = null
    ) = telegram.editMessageLiveLocation(chatId, messageId, inlineMessageId, latitude, longitude, replyMarkup)

    fun stopMessageLiveLocation(
        chatId: ChatId? = this.chatId,
        messageId: Long? = null,
        inlineMessageId: String? = null,
        replyMarkup: ReplyMarkup? = null
    ) = telegram.stopMessageLiveLocation(chatId, messageId, inlineMessageId, replyMarkup)

    fun sendVenue(
        latitude: Float,
        longitude: Float,
        title: String,
        address: String,
        foursquareId: String? = null,
        foursquareType: String? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendVenue(it, latitude, longitude, title, address, foursquareId, foursquareType, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendContact(
        phoneNumber: String,
        firstName: String,
        lastName: String? = null,
        disableNotification: Boolean? = null,
        replyToMessageId: Long? = null,
        replyMarkup: ReplyMarkup? = null
    ) = withChatId {
        telegram.sendContact(it, phoneNumber, firstName, lastName, disableNotification, replyToMessageId, replyMarkup)
    }

    fun sendChatAction(action: ChatAction) = withChatId {
        telegram.sendChatAction(it, action)
    }
}