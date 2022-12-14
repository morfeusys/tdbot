package com.justai.jaicf.channel.td

import com.justai.jaicf.model.activation.ActivationRule

typealias OnlyIf = ActivationRule.OnlyIfContext.() -> Boolean
private typealias IdProducer = () -> Long?

val isOutgoing: OnlyIf = { request.td?.isOutgoing == true }
val isIncoming: OnlyIf = { request.td?.isOutgoing == false }
val isChannelPost: OnlyIf = { request.td?.isChannelPost == true }
val isDirect: OnlyIf = { request.td?.isChannelPost == false && (request.td?.senderId == request.td?.chatId || request.td?.senderId == request.td?.user?.id) }
val isChat: (producer: IdProducer) -> OnlyIf = { p -> { request.td?.chatId != null && request.td?.chatId == p.invoke() } }
val isNotChat: (producer: IdProducer) -> OnlyIf = { p -> { request.td?.chatId != null && request.td?.chatId != p.invoke() } }
val isSender: (producer: IdProducer) -> OnlyIf = { p -> { request.td?.senderId != null && request.td?.senderId == p.invoke() } }
val isNotSender: (producer: IdProducer) -> OnlyIf = { p -> { request.td?.senderId != null && request.td?.senderId != p.invoke() } }

fun ActivationRule.ifOutgoing() = onlyIf(isOutgoing)
fun ActivationRule.ifIncoming() = onlyIf(isIncoming)
fun ActivationRule.ifChannelPost() = onlyIf(isChannelPost)
fun ActivationRule.ifDirect() = onlyIf(isDirect)
fun ActivationRule.ifChat(producer: IdProducer) = onlyIf(isChat(producer))
fun ActivationRule.ifNotChat(producer: IdProducer) = onlyIf(isNotChat(producer))
fun ActivationRule.ifSender(producer: IdProducer) = onlyIf(isSender(producer))
fun ActivationRule.ifNotSender(producer: IdProducer) = onlyIf(isNotSender(producer))