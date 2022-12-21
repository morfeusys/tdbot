package com.github.kotlintelegrambot.dispatcher

import com.github.kotlintelegrambot.dispatcher.handlers.*
import com.github.kotlintelegrambot.dispatcher.handlers.CallbackQueryHandler
import com.github.kotlintelegrambot.dispatcher.handlers.ChannelHandler
import com.github.kotlintelegrambot.dispatcher.handlers.CommandHandler
import com.github.kotlintelegrambot.dispatcher.handlers.ContactHandler
import com.github.kotlintelegrambot.dispatcher.handlers.DiceHandler
import com.github.kotlintelegrambot.dispatcher.handlers.InlineQueryHandler
import com.github.kotlintelegrambot.dispatcher.handlers.LocationHandler
import com.github.kotlintelegrambot.dispatcher.handlers.MessageHandler
import com.github.kotlintelegrambot.dispatcher.handlers.NewChatMembersHandler
import com.github.kotlintelegrambot.dispatcher.handlers.PollAnswerHandler
import com.github.kotlintelegrambot.dispatcher.handlers.PreCheckoutQueryHandler
import com.github.kotlintelegrambot.dispatcher.handlers.TextHandler
import com.github.kotlintelegrambot.dispatcher.handlers.media.AnimationHandler
import com.github.kotlintelegrambot.dispatcher.handlers.media.AudioHandler
import com.github.kotlintelegrambot.dispatcher.handlers.media.DocumentHandler
import com.github.kotlintelegrambot.dispatcher.handlers.media.GameHandler
import com.github.kotlintelegrambot.dispatcher.handlers.media.PhotosHandler
import com.github.kotlintelegrambot.dispatcher.handlers.media.StickerHandler
import com.github.kotlintelegrambot.dispatcher.handlers.media.VideoHandler
import com.github.kotlintelegrambot.dispatcher.handlers.media.VideoNoteHandler
import com.github.kotlintelegrambot.dispatcher.handlers.media.VoiceHandler
import com.github.kotlintelegrambot.extensions.filters.Filter
import com.github.kotlintelegrambot.extensions.filters.Filter.All

fun Dispatcher.message(handleMessage: HandleMessage) {
    addHandler(MessageHandler(All, handleMessage))
}

fun Dispatcher.message(filter: Filter, handleMessage: HandleMessage) {
    addHandler(MessageHandler(filter, handleMessage))
}

fun Dispatcher.command(command: String, handleCommand: HandleCommand) {
    addHandler(CommandHandler(command, handleCommand))
}

fun Dispatcher.text(text: String? = null, handleText: HandleText) {
    addHandler(TextHandler(text, handleText))
}

fun Dispatcher.callbackQuery(data: String? = null, handleCallbackQuery: HandleCallbackQuery) {
    addHandler(CallbackQueryHandler(callbackData = data, handleCallbackQuery = handleCallbackQuery))
}

fun Dispatcher.callbackQuery(
    callbackData: String? = null,
    callbackAnswerText: String? = null,
    callbackAnswerShowAlert: Boolean? = null,
    callbackAnswerUrl: String? = null,
    callbackAnswerCacheTime: Int? = null,
    handleCallbackQuery: HandleCallbackQuery
) {
    addHandler(
        CallbackQueryHandler(
            callbackData = callbackData,
            callbackAnswerText = callbackAnswerText,
            callbackAnswerShowAlert = callbackAnswerShowAlert,
            callbackAnswerUrl = callbackAnswerUrl,
            callbackAnswerCacheTime = callbackAnswerCacheTime,
            handleCallbackQuery = handleCallbackQuery
        )
    )
}

fun Dispatcher.contact(handleContact: HandleContact) {
    addHandler(ContactHandler(handleContact))
}

fun Dispatcher.location(handleLocation: HandleLocation) {
    addHandler(LocationHandler(handleLocation))
}

fun Dispatcher.telegramError(handleError: HandleError) {
    addErrorHandler(ErrorHandler(handleError))
}

fun Dispatcher.preCheckoutQuery(body: HandlePreCheckoutQuery) {
    addHandler(PreCheckoutQueryHandler(body))
}

fun Dispatcher.channel(body: HandleChannelPost) {
    addHandler(ChannelHandler(body))
}

fun Dispatcher.inlineQuery(body: HandleInlineQuery) {
    addHandler(InlineQueryHandler(body))
}

fun Dispatcher.audio(body: HandleAudio) {
    addHandler(AudioHandler(body))
}

fun Dispatcher.document(body: HandleDocument) {
    addHandler(DocumentHandler(body))
}

fun Dispatcher.animation(body: HandleAnimation) {
    addHandler(AnimationHandler(body))
}

fun Dispatcher.game(body: HandleGame) {
    addHandler(GameHandler(body))
}

fun Dispatcher.photos(body: HandlePhotos) {
    addHandler(PhotosHandler(body))
}

fun Dispatcher.sticker(body: HandleSticker) {
    addHandler(StickerHandler(body))
}

fun Dispatcher.video(body: HandleVideo) {
    addHandler(VideoHandler(body))
}

fun Dispatcher.voice(body: HandleVoice) {
    addHandler(VoiceHandler(body))
}

fun Dispatcher.videoNote(body: HandleVideoNote) {
    addHandler(VideoNoteHandler(body))
}

fun Dispatcher.newChatMembers(body: HandleNewChatMembers) {
    addHandler(NewChatMembersHandler(body))
}

fun Dispatcher.pollAnswer(body: HandlePollAnswer) {
    addHandler(PollAnswerHandler(body))
}

fun Dispatcher.dice(body: HandleDice) {
    addHandler(DiceHandler(body))
}

fun Dispatcher.webAppData(body: HandleWebAppData) {
    addHandler(WebAppDataHandler(body))
}