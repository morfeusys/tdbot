package com.justai.jaicf.channel.td

import com.justai.jaicf.model.activation.ActivationRule

typealias OnlyIf = ActivationRule.OnlyIfContext.() -> Boolean
private typealias IdProducer = ActivationRule.OnlyIfContext.() -> Long?
private typealias IdsProducer = ActivationRule.OnlyIfContext.() -> LongArray

val isOutgoing: OnlyIf = { request.td?.isOutgoing == true }
val isIncoming: OnlyIf = { request.td?.isOutgoing == false }
val isChannelPost: OnlyIf = { request.td?.isChannelPost == true }
val isNotChannelPost: OnlyIf = { request.td?.isChannelPost == false }

val isChat: (producer: IdProducer) -> OnlyIf = { p -> { request.td?.chatId?.let { it == p(this) } ?: false } }
val isNotChat: (producer: IdProducer) -> OnlyIf = { p -> { request.td?.chatId?.let { id -> p(this)?.let { id != it } } ?: false } }
val isChats: (producer: IdsProducer) -> OnlyIf = { p -> { request.td?.chatId?.let { p(this).contains(it) } ?: false } }
val isNotChats: (producer: IdsProducer) -> OnlyIf = { p -> { request.td?.chatId?.let { !p(this).contains(it) } ?: false } }

val isSender: (producer: IdProducer) -> OnlyIf = { p -> { request.td?.senderId?.let { it == p(this) } ?: false } }
val isNotSender: (producer: IdProducer) -> OnlyIf = { p -> { request.td?.senderId?.let { id -> p(this)?.let { id != it } } ?: false } }
val isSenders: (producer: IdsProducer) -> OnlyIf = { p -> { request.td?.senderId?.let { p(this).contains(it) } ?: false } }
val isNotSenders: (producer: IdsProducer) -> OnlyIf = { p -> { request.td?.senderId?.let { !p(this).contains(it) } ?: false } }

val isMyMessage: OnlyIf = isSender { request.td?.me?.id }
val isNotMyMessage: OnlyIf = isNotSender { request.td?.me?.id }

fun ActivationRule.ifOutgoing() = onlyIf(isOutgoing)
fun ActivationRule.ifIncoming() = onlyIf(isIncoming)
fun ActivationRule.ifChannelPost() = onlyIf(isChannelPost)
fun ActivationRule.isMyMessage() = onlyIf(isMyMessage)
fun ActivationRule.ifChat(producer: IdProducer) = onlyIf(isChat(producer))
fun ActivationRule.ifChats(producer: IdsProducer) = onlyIf(isChats(producer))
fun ActivationRule.ifNotChat(producer: IdProducer) = onlyIf(isNotChat(producer))
fun ActivationRule.ifNotChats(producer: IdsProducer) = onlyIf(isNotChats(producer))
fun ActivationRule.ifSender(producer: IdProducer) = onlyIf(isSender(producer))
fun ActivationRule.ifSenders(producer: IdsProducer) = onlyIf(isSenders(producer))
fun ActivationRule.ifNotSender(producer: IdProducer) = onlyIf(isNotSender(producer))
fun ActivationRule.ifNotSenders(producer: IdsProducer) = onlyIf(isNotSenders(producer))