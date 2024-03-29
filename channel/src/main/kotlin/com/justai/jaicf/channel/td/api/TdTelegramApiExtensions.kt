package com.justai.jaicf.channel.td.api

import com.justai.jaicf.channel.td.TdMessage
import it.tdlight.jni.TdApi

fun TdTelegramApi.parseText(
    text: String,
    parseMode: TdMessage.ParseMode?
) = parseMode?.let { send(TdApi.ParseTextEntities(text, parseMode.mode)) } ?: TdApi.FormattedText(text, emptyArray())

fun TdTelegramApi.sendMessage(
    chatId: Long,
    messageThreadId: Long = 0,
    replyToMessageId: Long = 0,
    options: TdApi.MessageSendOptions? = null,
    replyMarkup: TdApi.ReplyMarkup? = null,
    content: TdApi.InputMessageContent,
) = send(TdApi.SendMessage(chatId, messageThreadId, replyToMessageId, options, replyMarkup, content))

fun TdTelegramApi.addMessageReaction(
    chatId: Long,
    messageId: Long,
    reactionType: TdApi.ReactionType,
    isBig: Boolean = false,
    updateRecentReactions: Boolean = false,
) = send(TdApi.AddMessageReaction(chatId, messageId, reactionType, isBig, updateRecentReactions))

fun TdTelegramApi.forwardMessages(
    chatId: Long,
    messageThreadId: Long = 0,
    fromChatId: Long,
    messageIds: Array<Long>,
    options: TdApi.MessageSendOptions? = null,
    sendCopy: Boolean = false,
    removeCaption: Boolean = false,
    onlyPreview: Boolean = false,
) = send(TdApi.ForwardMessages(chatId, messageThreadId, fromChatId, messageIds.toLongArray(), options, sendCopy, removeCaption, onlyPreview))

fun TdTelegramApi.editMessageText(
    chatId: Long,
    messageId: Long,
    replyMarkup: TdApi.ReplyMarkup? = null,
    content: TdApi.InputMessageContent,
) = send(TdApi.EditMessageText(chatId, messageId, replyMarkup, content))

fun TdTelegramApi.editMessageMedia(
    chatId: Long,
    messageId: Long,
    replyMarkup: TdApi.ReplyMarkup? = null,
    content: TdApi.InputMessageContent,
) = send(TdApi.EditMessageMedia(chatId, messageId, replyMarkup, content))

fun TdTelegramApi.editMessageCaption(
    chatId: Long,
    messageId: Long,
    replyMarkup: TdApi.ReplyMarkup? = null,
    caption: TdApi.FormattedText,
) = send(TdApi.EditMessageCaption(chatId, messageId, replyMarkup, caption))

fun TdTelegramApi.editMessageReplyMarkup(
    chatId: Long,
    messageId: Long,
    replyMarkup: TdApi.ReplyMarkup
) = send(TdApi.EditMessageReplyMarkup(chatId, messageId, replyMarkup))

fun TdTelegramApi.getInlineQueryResults(
    botUserId: Long,
    chatId: Long,
    userLocation: TdApi.Location? = null,
    query: String,
    offset: String? = null,
) = send(TdApi.GetInlineQueryResults(botUserId, chatId, userLocation, query, offset))

fun TdTelegramApi.searchChats(query: String, limit: Int = 1) =
    send(TdApi.SearchChats(query, limit)).let {
        when (it.totalCount) {
            0 -> send(TdApi.SearchChatsOnServer(query, limit))
            else -> it
        }
    }

fun TdTelegramApi.searchPublicChats(query: String) =
    send(TdApi.SearchPublicChats(query))

fun TdTelegramApi.searchAnyChats(query: String, limit: Int = 1) =
    searchChats(query, limit).let {
        when (it.totalCount) {
            0 -> searchPublicChats(query)
            else -> it
        }
    }