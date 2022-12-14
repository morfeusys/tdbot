package com.justai.jaicf.channel.td

import it.tdlight.jni.TdApi

internal val TdApi.Update.clientId get() = when (this) {
    is TdApi.UpdateAccessHash -> accessHash.chatId
    is TdApi.UpdateAnimatedEmojiMessageClicked -> chatId
    is TdApi.UpdateBasicGroup -> basicGroup.id
    is TdApi.UpdateBasicGroupFullInfo -> basicGroupId
    is TdApi.UpdateCall -> call.userId
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
    is TdApi.UpdateChatMessageTtl -> chatId
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
    is TdApi.UpdateGroupCall -> groupCall.id
    is TdApi.UpdateGroupCallParticipant -> groupCallId
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
    is TdApi.UpdateNewCallSignalingData -> callId
    is TdApi.UpdateNewCallbackQuery -> chatId
    is TdApi.UpdateNewChat -> chat.id
    is TdApi.UpdateNewChatJoinRequest -> chatId
    is TdApi.UpdateNewChosenInlineResult -> senderUserId
    is TdApi.UpdateNewInlineCallbackQuery -> senderUserId
    is TdApi.UpdateNewInlineQuery -> senderUserId
    is TdApi.UpdateNewMessage -> message.chatId
    is TdApi.UpdateNewPreCheckoutQuery -> senderUserId
    is TdApi.UpdateNewShippingQuery -> senderUserId
    is TdApi.UpdatePoll -> poll.id
    is TdApi.UpdatePollAnswer -> userId
    is TdApi.UpdateSecretChat -> secretChat.id
    is TdApi.UpdateSupergroup -> supergroup.id
    is TdApi.UpdateSupergroupFullInfo -> supergroupId
    is TdApi.UpdateUser -> user.id
    is TdApi.UpdateUserFullInfo -> userId
    is TdApi.UpdateUserStatus -> userId
    is TdApi.UpdateWebAppMessageSent -> webAppLaunchId
    else -> null
}