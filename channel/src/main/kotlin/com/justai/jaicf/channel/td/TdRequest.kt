package com.justai.jaicf.channel.td

import com.justai.jaicf.api.BotRequest
import com.justai.jaicf.api.EventBotRequest
import com.justai.jaicf.api.QueryBotRequest
import com.justai.jaicf.channel.td.client.fromId
import com.justai.jaicf.channel.td.scenario.event
import it.tdlight.jni.TdApi

typealias DefaultTdRequest = TdRequest<out TdApi.Update>

val BotRequest.td get() = this as? DefaultTdRequest

val DefaultTdRequest.messageRequest get() = this as? TdMessageRequest<out TdApi.MessageContent>
val DefaultTdRequest.textRequest get() = this as? TdTextMessageRequest

val DefaultTdRequest.fromId get() = update.fromId
val DefaultTdRequest.messageId get() = messageRequest?.update?.message?.id
val DefaultTdRequest.chatId get() = messageRequest?.update?.message?.chatId
val DefaultTdRequest.senderId get() = messageRequest?.update?.senderId
val DefaultTdRequest.isOutgoing get() = messageRequest?.update?.message?.isOutgoing
val DefaultTdRequest.isChannelPost get() = messageRequest?.update?.message?.isChannelPost

val TdApi.UpdateNewMessage.senderId get() = when (message.senderId) {
    is TdApi.MessageSenderUser -> (message.senderId as TdApi.MessageSenderUser).userId
    is TdApi.MessageSenderChat -> (message.senderId as TdApi.MessageSenderChat).chatId
    else -> null
}

internal fun TdApi.Update.getClientId(user: TdApi.User) = fromId?.toString() ?: user.id.toString()

interface TdRequest<U : TdApi.Update> : BotRequest {
    val me: TdApi.User
    val update: U
}

interface TdMessageRequest<M : TdApi.MessageContent> : TdRequest<TdApi.UpdateNewMessage> {
    val content: M
}

data class TdUpdateRequest(
    override val me: TdApi.User,
    override val update: TdApi.Update
) : TdRequest<TdApi.Update>, EventBotRequest(clientId = update.getClientId(me), input = event(update))

data class TdTextMessageRequest(
    override val me: TdApi.User,
    override val update: TdApi.UpdateNewMessage,
): TdMessageRequest<TdApi.MessageText>, QueryBotRequest(clientId = update.getClientId(me), input = (update.message.content as TdApi.MessageText).text.text) {
    override val content = update.message.content as TdApi.MessageText
}

data class TdEventMessageRequest(
    override val me: TdApi.User,
    override val update: TdApi.UpdateNewMessage,
): TdMessageRequest<TdApi.MessageContent>, EventBotRequest(clientId = update.getClientId(me), input = event(update)) {
    override val content = update.message.content
}