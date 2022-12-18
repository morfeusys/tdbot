package com.justai.jaicf.channel.td

import com.justai.jaicf.model.activation.ActivationRule

typealias OnlyIf = ActivationRule.OnlyIfContext.() -> Boolean
private typealias IdProducer = ActivationRule.OnlyIfContext.() -> Long?

val isOutgoing: OnlyIf = { request.td?.isOutgoing == true }
val isIncoming: OnlyIf = { request.td?.isOutgoing == false }
val isChannelPost: OnlyIf = { request.td?.isChannelPost == true }
val isNotChannelPost: OnlyIf = { request.td?.isChannelPost == false }
val isChat: (producer: IdProducer) -> OnlyIf = { p -> { request.td?.chatId?.let { it == p(this) } ?: false } }
val isNotChat: (producer: IdProducer) -> OnlyIf = { p -> { request.td?.chatId?.let { id -> p(this)?.let { id != it } } ?: false } }
val isSender: (producer: IdProducer) -> OnlyIf = { p -> { request.td?.senderId?.let { it == p(this) } ?: false } }
val isNotSender: (producer: IdProducer) -> OnlyIf = { p -> { request.td?.senderId?.let { id -> p(this)?.let { id != it } } ?: false } }
val isMyMessage: OnlyIf = isSender { request.td?.me?.id }

fun ActivationRule.ifOutgoing() = onlyIf(isOutgoing)
fun ActivationRule.ifIncoming() = onlyIf(isIncoming)
fun ActivationRule.ifChannelPost() = onlyIf(isChannelPost)
fun ActivationRule.isMyMessage() = onlyIf(isMyMessage)
fun ActivationRule.ifChat(producer: IdProducer) = onlyIf(isChat(producer))
fun ActivationRule.ifNotChat(producer: IdProducer) = onlyIf(isNotChat(producer))
fun ActivationRule.ifSender(producer: IdProducer) = onlyIf(isSender(producer))
fun ActivationRule.ifNotSender(producer: IdProducer) = onlyIf(isNotSender(producer))