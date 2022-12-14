package com.justai.jaicf.channel.td

import com.justai.jaicf.api.BotRequest
import com.justai.jaicf.api.EventBotRequest
import com.justai.jaicf.api.QueryBotRequest
import it.tdlight.jni.TdApi

val BotRequest.td get() = this as? TdRequest

val TdRequest.message get() = this as? TdNewMessageRequest
val TdRequest.text get() = this as? TdNewTextMessageRequest

val TdRequest.messageId get() = message?.update?.message?.id
val TdRequest.chatId get() = message?.update?.message?.chatId
val TdRequest.senderId get() = message?.update?.senderId
val TdRequest.isOutgoing get() = message?.update?.message?.isOutgoing
val TdRequest.isChannelPost get() = message?.update?.message?.isChannelPost

val TdApi.UpdateNewMessage.senderId get() = when (message.senderId) {
    is TdApi.MessageSenderUser -> (message.senderId as TdApi.MessageSenderUser).userId
    is TdApi.MessageSenderChat -> (message.senderId as TdApi.MessageSenderChat).chatId
    else -> null
}

internal fun TdApi.Update.getClientId(user: TdApi.User) = clientId?.toString() ?: user.id.toString()

interface TdRequest : BotRequest {
    val user: TdApi.User
    val update: TdApi.Update
}

interface TdNewMessageRequest : TdRequest {
    override val update: TdApi.UpdateNewMessage
}

data class TdUpdateRequest(
    override val user: TdApi.User,
    override val update: TdApi.Update
) : TdRequest, EventBotRequest(clientId = update.getClientId(user), input = event(update))

data class TdNewTextMessageRequest(
    override val user: TdApi.User,
    override val update: TdApi.UpdateNewMessage,
    val content: TdApi.MessageText,
): TdNewMessageRequest, QueryBotRequest(clientId = update.getClientId(user), input = content.text.text)

data class TdNewEventMessageRequest(
    override val user: TdApi.User,
    override val update: TdApi.UpdateNewMessage,
): TdNewMessageRequest, EventBotRequest(clientId = update.getClientId(user), input = event(update))