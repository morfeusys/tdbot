package com.justai.jaicf.channel.td

import com.justai.jaicf.model.activation.ActivationRule

val isOutgoing: OnlyIf = { request.td?.isOutgoing == true }
val isIncoming: OnlyIf = { request.td?.isOutgoing == false }
val isChannelPost: OnlyIf = { request.td?.isChannelPost == true }
val isNotChannelPost: OnlyIf = { request.td?.isChannelPost == false }

val isFromId = createIdEqualsCondition { request.td?.fromId }
val isNotFromId = createIdNotEqualCondition { request.td?.fromId }
val isFromIds = createContainsIdCondition { request.td?.fromId }
val isNotFromIds = createNotContainIdCondition { request.td?.fromId }

val isChat = createIdEqualsCondition { request.td?.chatId }
val isNotChat = createIdNotEqualCondition { request.td?.chatId }
val isChats = createContainsIdCondition { request.td?.chatId }
val isNotChats = createNotContainIdCondition { request.td?.chatId }

val isSender = createIdEqualsCondition { request.td?.senderId }
val isNotSender = createIdNotEqualCondition { request.td?.senderId }
val isSenders = createContainsIdCondition { request.td?.senderId }
val isNotSenders = createNotContainIdCondition { request.td?.senderId }

val isMyMessage: OnlyIf = isSender { request.td?.me?.id }
val isNotMyMessage: OnlyIf = isNotSender { request.td?.me?.id }

typealias OnlyIf = ActivationRule.OnlyIfContext.() -> Boolean

private typealias IdProducer = ActivationRule.OnlyIfContext.() -> Long?
private typealias IdsProducer = ActivationRule.OnlyIfContext.() -> LongArray
private typealias OnlyIfId = (IdProducer) -> OnlyIf
private typealias OnlyIfIds = (IdsProducer) -> OnlyIf

private fun createIdCondition(idProducer: IdProducer, predicate: (Long, Long) -> Boolean): OnlyIfId 
    = { p -> { idProducer(this)?.let { id -> p(this)?.let { predicate(it, id) } } ?: false } }

private fun createIdsCondition(idProducer: IdProducer, predicate: (LongArray, Long) -> Boolean): OnlyIfIds
    = { p -> { idProducer(this)?.let { id -> predicate(p(this), id) } ?: false }}

private fun createIdEqualsCondition(idProducer: IdProducer) = createIdCondition(idProducer) { id1, id2 -> id1 == id2 }
private fun createIdNotEqualCondition(idProducer: IdProducer) = createIdCondition(idProducer) { id1, id2 -> id1 != id2 }
private fun createContainsIdCondition(idProducer: IdProducer) = createIdsCondition(idProducer) { ids, id -> ids.contains(id) }
private fun createNotContainIdCondition(idProducer: IdProducer) = createIdsCondition(idProducer) { ids, id -> !ids.contains(id) }