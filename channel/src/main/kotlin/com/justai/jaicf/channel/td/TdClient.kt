package com.justai.jaicf.channel.td

import it.tdlight.client.GenericResultHandler
import it.tdlight.client.Result
import it.tdlight.client.SimpleTelegramClient
import it.tdlight.jni.TdApi

class DefaultResultHandler<R : TdApi.Object> : GenericResultHandler<R> {
    override fun onResult(result: Result<R>?) {}
}

fun SimpleTelegramClient.sendMessage(
    chatId: Long,
    messageThreadId: Long = 0,
    replyToMessageId: Long = 0,
    options: TdApi.MessageSendOptions? = null,
    replyMarkup: TdApi.ReplyMarkup? = null,
    content: TdApi.InputMessageContent,
    handler: GenericResultHandler<TdApi.Message> = DefaultResultHandler()
) = send(
    TdApi.SendMessage(chatId, messageThreadId, replyToMessageId, options, replyMarkup, content),
    handler
)

fun SimpleTelegramClient.forwardMessages(
    chatId: Long,
    messageThreadId: Long = 0,
    fromChatId: Long,
    messageIds: Array<Long>,
    options: TdApi.MessageSendOptions? = null,
    sendCopy: Boolean = false,
    removeCaption: Boolean = false,
    onlyPreview: Boolean = false,
    handler: GenericResultHandler<TdApi.Messages> = DefaultResultHandler()
) = send(
    TdApi.ForwardMessages(chatId, messageThreadId, fromChatId, messageIds.toLongArray(), options, sendCopy, removeCaption, onlyPreview),
    handler
)

fun SimpleTelegramClient.editMessageText(
    chatId: Long,
    messageId: Long,
    replyMarkup: TdApi.ReplyMarkup? = null,
    content: TdApi.InputMessageContent,
    handler: GenericResultHandler<TdApi.Message> = DefaultResultHandler()
) = send(TdApi.EditMessageText(chatId, messageId, replyMarkup, content), handler)

fun SimpleTelegramClient.editMessageMedia(
    chatId: Long,
    messageId: Long,
    replyMarkup: TdApi.ReplyMarkup? = null,
    content: TdApi.InputMessageContent,
    handler: GenericResultHandler<TdApi.Message> = DefaultResultHandler()
) = send(TdApi.EditMessageMedia(chatId, messageId, replyMarkup, content), handler)

fun SimpleTelegramClient.editMessageCaption(
    chatId: Long,
    messageId: Long,
    replyMarkup: TdApi.ReplyMarkup? = null,
    caption: TdApi.FormattedText,
    handler: GenericResultHandler<TdApi.Message> = DefaultResultHandler()
) = send(TdApi.EditMessageCaption(chatId, messageId, replyMarkup, caption), handler)

fun SimpleTelegramClient.getInlineQueryResults(
    botUserId: Long,
    chatId: Long,
    userLocation: TdApi.Location? = null,
    query: String,
    offset: String? = null,
    handler: GenericResultHandler<TdApi.InlineQueryResults>
) = send(TdApi.GetInlineQueryResults(botUserId, chatId, userLocation, query, offset), handler)