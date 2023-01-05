package com.justai.jaicf.channel.td

import it.tdlight.jni.TdApi

object Td {
    fun text(messageText: TdApi.MessageText) =
        text(messageText.text.text, messageText.text.entities)

    fun text(
        text: String,
        entities: Array<out TdApi.TextEntity> = emptyArray(),
        disableWebPagePreview: Boolean = false,
        clearDraft: Boolean = false,
    ) = TdApi.InputMessageText(TdApi.FormattedText(text, entities), disableWebPagePreview, clearDraft)

    fun photo(
        id: String,
        thumbnail: TdApi.InputThumbnail? = null,
        addedStickerFileIds: IntArray? = null,
        width: Int = 0,
        height: Int = 0,
        caption: TdApi.FormattedText? = null,
        selfDestructTime: Int = 0,
        hasSpoiler: Boolean = false,
    ) = photo(TdApi.InputFileRemote(id), thumbnail, addedStickerFileIds, width, height, caption, selfDestructTime, hasSpoiler)

    fun photo(
        file: TdApi.InputFile,
        thumbnail: TdApi.InputThumbnail? = null,
        addedStickerFileIds: IntArray? = null,
        width: Int = 0,
        height: Int = 0,
        caption: TdApi.FormattedText? = null,
        selfDestructTime: Int = 0,
        hasSpoiler: Boolean = false,
    ) = TdApi.InputMessagePhoto(file, thumbnail, addedStickerFileIds, width, height, caption, selfDestructTime, hasSpoiler)

    fun animation(
        id: String,
        thumbnail: TdApi.InputThumbnail? = null,
        addedStickerFileIds: IntArray? = null,
        duration: Int = 0,
        width: Int = 0,
        height: Int = 0,
        caption: TdApi.FormattedText? = null,
        hasSpoiler: Boolean = false,
    ) = animation(TdApi.InputFileRemote(id), thumbnail, addedStickerFileIds, duration, width, height, caption, hasSpoiler)

    fun animation(
        file: TdApi.InputFile,
        thumbnail: TdApi.InputThumbnail? = null,
        addedStickerFileIds: IntArray? = null,
        duration: Int = 0,
        width: Int = 0,
        height: Int = 0,
        caption: TdApi.FormattedText? = null,
        hasSpoiler: Boolean = false,
    ) = TdApi.InputMessageAnimation(file, thumbnail, addedStickerFileIds, duration, width, height, caption, hasSpoiler)

    fun audio(
        id: String,
        albumCoverThumbnail: TdApi.InputThumbnail? = null,
        duration: Int = 0,
        title: String? = null,
        performer: String? = null,
        caption: TdApi.FormattedText? = null,
    ) = audio(TdApi.InputFileRemote(id), albumCoverThumbnail, duration, title, performer, caption)

    fun audio(
        file: TdApi.InputFile,
        albumCoverThumbnail: TdApi.InputThumbnail? = null,
        duration: Int = 0,
        title: String? = null,
        performer: String? = null,
        caption: TdApi.FormattedText? = null
    ) = TdApi.InputMessageAudio(file, albumCoverThumbnail, duration, title, performer, caption)

    fun contact(
        contact: TdApi.Contact
    ) = TdApi.InputMessageContact(contact)

    fun dice(
        emoji: String,
        clearDraft: Boolean = false
    ) = TdApi.InputMessageDice(emoji, clearDraft)

    fun document(
        file: TdApi.InputFile,
        thumbnail: TdApi.InputThumbnail? = null,
        disableContentTypeDetection: Boolean = false,
        caption: TdApi.FormattedText? = null
    ) = TdApi.InputMessageDocument(file, thumbnail, disableContentTypeDetection, caption)

    fun forwarded(
        fromChatId: Long,
        messageId: Long,
        inGameShare: Boolean = false,
        copyOptions: TdApi.MessageCopyOptions? = null
    ) = TdApi.InputMessageForwarded(fromChatId, messageId, inGameShare, copyOptions)

    fun game(
        botUserId: Long,
        gameShortName: String,
    ) = TdApi.InputMessageGame(botUserId, gameShortName)

    fun invoice(
        invoice: TdApi.Invoice,
        title: String,
        description: String,
        photoUrl: String,
        photoSize: Int = 0,
        photoWidth: Int = 0,
        photoHeight: Int = 0,
        payload: ByteArray,
        providerToken: String,
        providerData: String,
        startParameter: String,
        extendedMediaContent: TdApi.InputMessageContent? = null
    ) = TdApi.InputMessageInvoice(
        invoice,
        title,
        description,
        photoUrl,
        photoSize,
        photoWidth,
        photoHeight,
        payload,
        providerToken,
        providerData,
        startParameter,
        extendedMediaContent
    )

    fun location(
        location: TdApi.Location,
        livePeriod: Int = 0,
        heading: Int = 0,
        proximityAlertRadius: Int = 0
    ) = TdApi.InputMessageLocation(location, livePeriod, heading, proximityAlertRadius)

    fun poll(
        question: String,
        options: Array<String>,
        isAnonymous: Boolean = false,
        type: TdApi.PollType = TdApi.PollTypeRegular(),
        openPeriod: Int = 0,
        closeDate: Int = 0,
        isClosed: Boolean = false
    ) = TdApi.InputMessagePoll(question, options, isAnonymous, type, openPeriod, closeDate, isClosed)

    fun sticker(
        id: String,
        thumbnail: TdApi.InputThumbnail? = null,
        width: Int = 0,
        height: Int = 0,
        emoji: String? = null
    ) = sticker(TdApi.InputFileRemote(id), thumbnail, width, height, emoji)

    fun sticker(
        file: TdApi.InputFile,
        thumbnail: TdApi.InputThumbnail? = null,
        width: Int = 0,
        height: Int = 0,
        emoji: String? = null
    ) = TdApi.InputMessageSticker(file, thumbnail, width, height, emoji)

    fun venue(
        venue: TdApi.Venue
    ) = TdApi.InputMessageVenue(venue)

    fun video(
        id: String,
        thumbnail: TdApi.InputThumbnail? = null,
        addedStickerFileIds: IntArray? = null,
        duration: Int = 0,
        width: Int = 0,
        height: Int = 0,
        supportsStreaming: Boolean = false,
        caption: TdApi.FormattedText? = null,
        ttl: Int = 0
    ) = video(TdApi.InputFileRemote(id), thumbnail, addedStickerFileIds, duration, width, height, supportsStreaming, caption, ttl)

    fun video(
        file: TdApi.InputFile,
        thumbnail: TdApi.InputThumbnail? = null,
        addedStickerFileIds: IntArray?,
        duration: Int = 0,
        width: Int = 0,
        height: Int = 0,
        supportsStreaming: Boolean = false,
        caption: TdApi.FormattedText? = null,
        ttl: Int = 0,
        hasSpoiler: Boolean = false,
    ) = TdApi.InputMessageVideo(file, thumbnail, addedStickerFileIds, duration, width, height, supportsStreaming, caption, ttl, hasSpoiler)

    fun videoNote(
        id: String,
        thumbnail: TdApi.InputThumbnail? = null,
        duration: Int = 0,
        length: Int = 0
    ) = videoNote(TdApi.InputFileRemote(id), thumbnail, duration, length)

    fun videoNote(
        file: TdApi.InputFile,
        thumbnail: TdApi.InputThumbnail? = null,
        duration: Int = 0,
        length: Int = 0
    ) = TdApi.InputMessageVideoNote(file, thumbnail, duration, length)

    fun voiceNote(
        id: String,
        duration: Int = 0,
        waveForm: ByteArray? = null,
        caption: TdApi.FormattedText? = null,
    ) = voiceNote(TdApi.InputFileRemote(id), duration, waveForm, caption)

    fun voiceNote(
        file: TdApi.InputFile,
        duration: Int = 0,
        waveForm: ByteArray? = null,
        caption: TdApi.FormattedText? = null,
    ) = TdApi.InputMessageVoiceNote(file, duration, waveForm, caption)
}

