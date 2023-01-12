package com.justai.jaicf.channel.td.api

import com.justai.jaicf.channel.td.request.TdEventUpdate
import it.tdlight.jni.TdApi

val TdApi.Update.messageId: Long? get() = when (this) {
    is TdApi.UpdateNewMessage -> message.id
    is TdApi.UpdateMessageEdited -> messageId
    is TdApi.UpdateMessageContent -> messageId
    is TdApi.UpdateMessageContentOpened -> messageId
    is TdApi.UpdateMessageInteractionInfo -> messageId
    is TdApi.UpdateMessageIsPinned -> messageId
    is TdApi.UpdateMessageLiveLocationViewed -> messageId
    is TdApi.UpdateMessageMentionRead -> messageId
    is TdApi.UpdateMessageSendAcknowledged -> messageId
    is TdApi.UpdateMessageSendFailed -> message.id
    is TdApi.UpdateMessageSendSucceeded -> message.id
    is TdApi.UpdateMessageUnreadReactions -> messageId
    else -> null
}

val TdApi.Update.chatId: Long? get() = when (this) {
    is TdEventUpdate -> chatId
    is TdApi.UpdateAccessHash -> accessHash.chatId
    is TdApi.UpdateAnimatedEmojiMessageClicked -> chatId
    is TdApi.UpdateNewCallbackQuery -> chatId
    is TdApi.UpdateNewChat -> chat.id
    is TdApi.UpdateNewChatJoinRequest -> chatId
    is TdApi.UpdateChatAction -> chatId
    is TdApi.UpdateChatActionBar -> chatId
    is TdApi.UpdateChatAvailableReactions -> chatId
    is TdApi.UpdateChatDefaultDisableNotification -> chatId
    is TdApi.UpdateChatDraftMessage -> chatId
    is TdApi.UpdateChatHasProtectedContent -> chatId
    is TdApi.UpdateChatHasScheduledMessages -> chatId
    is TdApi.UpdateChatIsBlocked -> chatId
    is TdApi.UpdateChatIsMarkedAsUnread -> chatId
    is TdApi.UpdateChatLastMessage -> chatId
    is TdApi.UpdateChatMember -> chatId
    is TdApi.UpdateChatMessageSender -> chatId
    is TdApi.UpdateChatMessageAutoDeleteTime -> chatId
    is TdApi.UpdateChatNotificationSettings -> chatId
    is TdApi.UpdateChatOnlineMemberCount -> chatId
    is TdApi.UpdateChatPendingJoinRequests -> chatId
    is TdApi.UpdateChatPermissions -> chatId
    is TdApi.UpdateChatPhoto -> chatId
    is TdApi.UpdateChatPosition -> chatId
    is TdApi.UpdateChatReadInbox -> chatId
    is TdApi.UpdateChatReadOutbox -> chatId
    is TdApi.UpdateChatReplyMarkup -> chatId
    is TdApi.UpdateChatTheme -> chatId
    is TdApi.UpdateChatTitle -> chatId
    is TdApi.UpdateChatUnreadMentionCount -> chatId
    is TdApi.UpdateChatUnreadReactionCount -> chatId
    is TdApi.UpdateChatVideoChat -> chatId
    is TdApi.UpdateDeleteMessages -> chatId
    is TdApi.UpdateForumTopicInfo -> chatId
    is TdApi.UpdateMessageContent -> chatId
    is TdApi.UpdateMessageContentOpened -> chatId
    is TdApi.UpdateMessageEdited -> chatId
    is TdApi.UpdateMessageInteractionInfo -> chatId
    is TdApi.UpdateMessageIsPinned -> chatId
    is TdApi.UpdateMessageLiveLocationViewed -> chatId
    is TdApi.UpdateMessageMentionRead -> chatId
    is TdApi.UpdateMessageSendAcknowledged -> chatId
    is TdApi.UpdateMessageSendFailed -> message.chatId
    is TdApi.UpdateMessageSendSucceeded -> message.chatId
    is TdApi.UpdateMessageUnreadReactions -> chatId
    is TdApi.UpdateNewMessage -> message.chatId
    is TdApi.UpdateSecretChat -> secretChat.id.toLong()
    else -> null
}

val TdApi.Update.userId: Long? get() = when (this) {
    is TdApi.UpdateCall -> call.userId
    is TdApi.UpdateNewChosenInlineResult -> senderUserId
    is TdApi.UpdateNewInlineCallbackQuery -> senderUserId
    is TdApi.UpdateNewInlineQuery -> senderUserId
    is TdApi.UpdateNewPreCheckoutQuery -> senderUserId
    is TdApi.UpdateNewShippingQuery -> senderUserId
    is TdApi.UpdatePollAnswer -> userId
    is TdApi.UpdateUser -> user.id
    is TdApi.UpdateUserFullInfo -> userId
    is TdApi.UpdateUserStatus -> userId
    else -> null
}

val TdApi.Update.senderId: Long? get() = when (this) {
    is TdApi.UpdateNewCallbackQuery -> senderUserId
    is TdApi.UpdateNewMessage -> when (message.senderId) {
        is TdApi.MessageSenderUser -> (message.senderId as TdApi.MessageSenderUser).userId
        is TdApi.MessageSenderChat -> (message.senderId as TdApi.MessageSenderChat).chatId
        else -> null
    }
    else -> null
}

val TdApi.Update.fromId: Long? get() = when (this) {
    is TdApi.UpdateBasicGroup -> basicGroup.id
    is TdApi.UpdateBasicGroupFullInfo -> basicGroupId
    is TdApi.UpdateGroupCall -> groupCall.id.toLong()
    is TdApi.UpdateGroupCallParticipant -> groupCallId.toLong()
    is TdApi.UpdateNewCallSignalingData -> callId.toLong()
    is TdApi.UpdatePoll -> poll.id
    is TdApi.UpdateSupergroup -> supergroup.id
    is TdApi.UpdateSupergroupFullInfo -> supergroupId
    is TdApi.UpdateWebAppMessageSent -> webAppLaunchId
    else -> senderId ?: userId ?: chatId
}

val TdApi.UpdateNewCallbackQuery.stringPayload: String
    get() {
        val p = payload
        return when (p) {
            is TdApi.CallbackQueryPayloadData -> String(p.data)
            is TdApi.CallbackQueryPayloadDataWithPassword -> String(p.data)
            is TdApi.CallbackQueryPayloadGame -> p.gameShortName
            else -> ""
        }
    }