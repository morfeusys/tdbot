package com.justai.jaicf.channel.td

import it.tdlight.jni.TdApi

object Td

fun Td.text(
    text: String,
    entities: Array<out TdApi.TextEntity> = emptyArray(),
    disableWebPagePreview: Boolean = false,
    clearDraft: Boolean = false,
) = TdApi.InputMessageText(TdApi.FormattedText(text, entities), disableWebPagePreview, clearDraft)

fun Td.photo(
    id: String,
    thumbnail: TdApi.InputThumbnail? = null,
    addedStickerFileIds: IntArray? = null,
    width: Int = 0,
    height: Int = 0,
    caption: TdApi.FormattedText? = null,
    ttl: Int = 0,
) = photo(TdApi.InputFileRemote(id), thumbnail, addedStickerFileIds, width, height, caption, ttl)

fun Td.photo(
    file: TdApi.InputFile,
    thumbnail: TdApi.InputThumbnail? = null,
    addedStickerFileIds: IntArray? = null,
    width: Int = 0,
    height: Int = 0,
    caption: TdApi.FormattedText? = null,
    ttl: Int = 0,
) = TdApi.InputMessagePhoto(file, thumbnail, addedStickerFileIds, width, height, caption, ttl)

fun Td.animation(
    id: String,
    thumbnail: TdApi.InputThumbnail? = null,
    addedStickerFileIds: IntArray? = null,
    duration: Int = 0,
    width: Int = 0,
    height: Int = 0,
    caption: TdApi.FormattedText? = null,
) = animation(TdApi.InputFileRemote(id), thumbnail, addedStickerFileIds, duration, width, height, caption)

fun Td.animation(
    file: TdApi.InputFile,
    thumbnail: TdApi.InputThumbnail? = null,
    addedStickerFileIds: IntArray? = null,
    duration: Int = 0,
    width: Int = 0,
    height: Int = 0,
    caption: TdApi.FormattedText? = null,
) = TdApi.InputMessageAnimation(file, thumbnail, addedStickerFileIds, duration, width, height, caption)

fun Td.audio(
    id: String,
    albumCoverThumbnail: TdApi.InputThumbnail? = null,
    duration: Int = 0,
    title: String? = null,
    performer: String? = null,
    caption: TdApi.FormattedText? = null
) = audio(TdApi.InputFileRemote(id), albumCoverThumbnail, duration, title, performer, caption)

fun Td.audio(
    file: TdApi.InputFile,
    albumCoverThumbnail: TdApi.InputThumbnail? = null,
    duration: Int = 0,
    title: String? = null,
    performer: String? = null,
    caption: TdApi.FormattedText? = null
) = TdApi.InputMessageAudio(file, albumCoverThumbnail, duration, title, performer, caption)

fun Td.contact(
    contact: TdApi.Contact
) = TdApi.InputMessageContact(contact)

fun Td.dice(
    emoji: String,
    clearDraft: Boolean = false
) = TdApi.InputMessageDice(emoji, clearDraft)

fun Td.document(
    file: TdApi.InputFile,
    thumbnail: TdApi.InputThumbnail? = null,
    disableContentTypeDetection: Boolean = false,
    caption: TdApi.FormattedText? = null
) = TdApi.InputMessageDocument(file, thumbnail, disableContentTypeDetection, caption)

fun Td.forwarded(
    fromChatId: Long,
    messageId: Long,
    inGameShare: Boolean = false,
    copyOptions: TdApi.MessageCopyOptions? = null
) = TdApi.InputMessageForwarded(fromChatId, messageId, inGameShare, copyOptions)

fun Td.game(
    botUserId: Long,
    gameShortName: String,
) = TdApi.InputMessageGame(botUserId, gameShortName)

fun Td.invoice(
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

fun Td.location(
    location: TdApi.Location,
    livePeriod: Int = 0,
    heading: Int = 0,
    proximityAlertRadius: Int = 0
) = TdApi.InputMessageLocation(location, livePeriod, heading, proximityAlertRadius)

fun Td.poll(
    question: String,
    options: Array<String>,
    isAnonymous: Boolean = false,
    type: TdApi.PollType = TdApi.PollTypeRegular(),
    openPeriod: Int = 0,
    closeDate: Int = 0,
    isClosed: Boolean = false
) = TdApi.InputMessagePoll(question, options, isAnonymous, type, openPeriod, closeDate, isClosed)

fun Td.sticker(
    id: String,
    thumbnail: TdApi.InputThumbnail? = null,
    width: Int = 0,
    height: Int = 0,
    emoji: String? = null
) = sticker(TdApi.InputFileRemote(id), thumbnail, width, height, emoji)

fun Td.sticker(
    file: TdApi.InputFile,
    thumbnail: TdApi.InputThumbnail? = null,
    width: Int = 0,
    height: Int = 0,
    emoji: String? = null
) = TdApi.InputMessageSticker(file, thumbnail, width, height, emoji)

fun Td.venue(
    venue: TdApi.Venue
) = TdApi.InputMessageVenue(venue)

fun Td.video(
    id: String,
    thumbnail: TdApi.InputThumbnail? = null,
    addedStickerFileIds: IntArray?,
    duration: Int = 0,
    width: Int = 0,
    height: Int = 0,
    supportsStreaming: Boolean = false,
    caption: TdApi.FormattedText? = null,
    ttl: Int = 0
) = video(TdApi.InputFileRemote(id), thumbnail, addedStickerFileIds, duration, width, height, supportsStreaming, caption, ttl)

fun Td.video(
    file: TdApi.InputFile,
    thumbnail: TdApi.InputThumbnail? = null,
    addedStickerFileIds: IntArray?,
    duration: Int = 0,
    width: Int = 0,
    height: Int = 0,
    supportsStreaming: Boolean = false,
    caption: TdApi.FormattedText? = null,
    ttl: Int = 0
) = TdApi.InputMessageVideo(file, thumbnail, addedStickerFileIds, duration, width, height, supportsStreaming, caption, ttl)

fun Td.videoNote(
    id: String,
    thumbnail: TdApi.InputThumbnail? = null,
    duration: Int = 0,
    length: Int = 0
) = videoNote(TdApi.InputFileRemote(id), thumbnail, duration, length)

fun Td.videoNote(
    file: TdApi.InputFile,
    thumbnail: TdApi.InputThumbnail? = null,
    duration: Int = 0,
    length: Int = 0
) = TdApi.InputMessageVideoNote(file, thumbnail, duration, length)

fun Td.voiceNote(
    id: String,
    duration: Int = 0,
    waveForm: ByteArray? = null,
    caption: TdApi.FormattedText? = null,
) = voiceNote(TdApi.InputFileRemote(id), duration, waveForm, caption)

fun Td.voiceNote(
    file: TdApi.InputFile,
    duration: Int = 0,
    waveForm: ByteArray? = null,
    caption: TdApi.FormattedText? = null,
) = TdApi.InputMessageVoiceNote(file, duration, waveForm, caption)