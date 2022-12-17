package com.justai.jaicf.channel.td

import com.justai.jaicf.api.BotRequest
import com.justai.jaicf.api.EventBotRequest
import com.justai.jaicf.api.QueryBotRequest
import it.tdlight.jni.TdApi

typealias DefaultTdRequest = TdRequest<out TdApi.Update>

val BotRequest.td get() = this as? DefaultTdRequest

val DefaultTdRequest.message get() = this as? TdNewMessageRequest<out TdApi.MessageContent>
val DefaultTdRequest.text get() = this as? TdNewTextMessageRequest

val DefaultTdRequest.messageId get() = message?.update?.message?.id
val DefaultTdRequest.chatId get() = message?.update?.message?.chatId
val DefaultTdRequest.senderId get() = message?.update?.senderId
val DefaultTdRequest.isOutgoing get() = message?.update?.message?.isOutgoing
val DefaultTdRequest.isChannelPost get() = message?.update?.message?.isChannelPost

val TdApi.UpdateNewMessage.senderId get() = when (message.senderId) {
    is TdApi.MessageSenderUser -> (message.senderId as TdApi.MessageSenderUser).userId
    is TdApi.MessageSenderChat -> (message.senderId as TdApi.MessageSenderChat).chatId
    else -> null
}

internal fun TdApi.Update.getClientId(user: TdApi.User) = clientId?.toString() ?: user.id.toString()

interface TdRequest<U : TdApi.Update> : BotRequest {
    val user: TdApi.User
    val update: U
}

interface TdNewMessageRequest<M : TdApi.MessageContent> : TdRequest<TdApi.UpdateNewMessage> {
    val content: M
}

data class TdUpdateRequest(
    override val user: TdApi.User,
    override val update: TdApi.Update
) : TdRequest<TdApi.Update>, EventBotRequest(clientId = update.getClientId(user), input = event(update))

data class TdNewTextMessageRequest(
    override val user: TdApi.User,
    override val update: TdApi.UpdateNewMessage,
): TdNewMessageRequest<TdApi.MessageText>, QueryBotRequest(clientId = update.getClientId(user), input = (update.message.content as TdApi.MessageText).text.text) {
    override val content = update.message.content as TdApi.MessageText
}

data class TdNewEventMessageRequest(
    override val user: TdApi.User,
    override val update: TdApi.UpdateNewMessage,
): TdNewMessageRequest<TdApi.MessageContent>, EventBotRequest(clientId = update.getClientId(user), input = event(update)) {
    override val content = update.message.content
}