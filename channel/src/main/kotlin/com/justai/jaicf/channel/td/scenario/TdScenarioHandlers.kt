package com.justai.jaicf.channel.td.scenario

import com.justai.jaicf.builder.ScenarioDsl
import com.justai.jaicf.channel.td.*
import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.context.ActivatorContext
import com.justai.jaicf.plugin.StateBody
import com.justai.jaicf.plugin.StateDeclaration
import it.tdlight.jni.TdApi

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onTextMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageText>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onAnimatedEmojiMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageAnimatedEmoji>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onAnimationMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageAnimation>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onAudioMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageAudio>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onBasicGroupChatCreateMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageBasicGroupChatCreate>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onCallMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageCall>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onChatAddMembersMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageChatAddMembers>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onChatChangePhotoMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageChatChangePhoto>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onChatChangeTitleMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageChatChangeTitle>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onChatDeleteMemberMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageChatDeleteMember>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onChatDeletePhotoMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageChatDeletePhoto>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onChatJoinByLinkMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageChatJoinByLink>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onChatJoinByRequestMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageChatJoinByRequest>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onChatSetThemeMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageChatSetTheme>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onChatSetAutoDeleteTimeMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageChatSetMessageAutoDeleteTime>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onChatUpgradeFromMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageChatUpgradeFrom>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onChatUpgradeToMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageChatUpgradeTo>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onContactMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageContact>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onContactRegisteredMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageContactRegistered>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onCustomServiceActionMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageCustomServiceAction>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onDiceMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageDice>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onDocumentMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageDocument>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onExpiredPhotoMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageExpiredPhoto>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onExpiredVideoMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageExpiredVideo>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onForumTopicCreatedMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageForumTopicCreated>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onForumTopicEditedMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageForumTopicEdited>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onForumTopicIsClosedToggledMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageForumTopicIsClosedToggled>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onGameMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageGame>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onGameScoreMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageGameScore>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onGiftedPremiumMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageGiftedPremium>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onInviteVideoChatParticipantsMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageInviteVideoChatParticipants>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onInvoiceMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageInvoice>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onLocationMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageLocation>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onPassportDataReceivedMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessagePassportDataReceived>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onPassportDataSentMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessagePassportDataSent>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onPaymentSuccessfulMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessagePaymentSuccessful>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onPaymentSuccessfulBotMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessagePaymentSuccessfulBot>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onPhotoMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessagePhoto>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onPinMessageMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessagePinMessage>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onPollMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessagePoll>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onProximityAlertTriggeredMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageProximityAlertTriggered>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onScreenshotTakenMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageScreenshotTaken>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onStickerMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageSticker>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onSupergroupChatCreateMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageSupergroupChatCreate>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUnsupportedMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageUnsupported>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onVenueMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageVenue>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onVideoMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageVideo>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onVideoChatEndedMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageVideoChatEnded>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onVideoChatScheduledMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageVideoChatScheduled>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onVideoChatStartedMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageVideoChatStarted>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onVideoNoteMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageVideoNote>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onVoiceNoteMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageVoiceNote>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onWebAppDataReceivedMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageWebAppDataReceived>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onWebAppDataSentMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageWebAppDataSent>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onWebsiteConnectedMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.MessageWebsiteConnected>, TdReactions>.() -> Unit
) = onNewMessage(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateAccessHash(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateAccessHash>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateActiveEmojiReactions(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateActiveEmojiReactions>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateActiveNotifications(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateActiveNotifications>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateAnimatedEmojiMessageClicked(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateAnimatedEmojiMessageClicked>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateAnimationSearchParameters(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateAnimationSearchParameters>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateAttachmentMenuBots(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateAttachmentMenuBots>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateAuthorizationState(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateAuthorizationState>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateBasicGroup(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateBasicGroup>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateBasicGroupFullInfo(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateBasicGroupFullInfo>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateCall(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateCall>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatAction(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatAction>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatActionBar(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatActionBar>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatAvailableReactions(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatAvailableReactions>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatDefaultDisableNotification(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatDefaultDisableNotification>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatDraftMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatDraftMessage>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatFilters(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatFilters>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatHasProtectedContent(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatHasProtectedContent>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatHasScheduledMessages(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatHasScheduledMessages>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatIsBlocked(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatIsBlocked>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatIsMarkedAsUnread(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatIsMarkedAsUnread>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatLastMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatLastMessage>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatMember(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatMember>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatMessageSender(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatMessageSender>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatMessageAutoDeleteTime(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatMessageAutoDeleteTime>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatNotificationSettings(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatNotificationSettings>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatOnlineMemberCount(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatOnlineMemberCount>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatPendingJoinRequests(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatPendingJoinRequests>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatPermissions(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatPermissions>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatPhoto(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatPhoto>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatPosition(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatPosition>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatReadInbox(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatReadInbox>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatReadOutbox(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatReadOutbox>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatReplyMarkup(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatReplyMarkup>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatTheme(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatTheme>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatThemes(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatThemes>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatTitle(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatTitle>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatUnreadMentionCount(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatUnreadMentionCount>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatUnreadReactionCount(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatUnreadReactionCount>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateChatVideoChat(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateChatVideoChat>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateConnectionState(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateConnectionState>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateDefaultReactionType(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateDefaultReactionType>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateDeleteMessages(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateDeleteMessages>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateDiceEmojis(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateDiceEmojis>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateFavoriteStickers(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateFavoriteStickers>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateFile(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateFile>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateFileAddedToDownloads(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateFileAddedToDownloads>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateFileDownload(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateFileDownload>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateFileDownloads(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateFileDownloads>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateFileGenerationStart(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateFileGenerationStart>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateFileGenerationStop(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateFileGenerationStop>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateFileRemovedFromDownloads(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateFileRemovedFromDownloads>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateForumTopicInfo(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateForumTopicInfo>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateGroupCall(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateGroupCall>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateGroupCallParticipant(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateGroupCallParticipant>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateHavePendingNotifications(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateHavePendingNotifications>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateInstalledStickerSets(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateInstalledStickerSets>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateLanguagePackStrings(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateLanguagePackStrings>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateMessageContent(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateMessageContent>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateMessageContentOpened(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateMessageContentOpened>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateMessageEdited(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateMessageEdited>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateMessageInteractionInfo(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateMessageInteractionInfo>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateMessageIsPinned(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateMessageIsPinned>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateMessageLiveLocationViewed(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateMessageLiveLocationViewed>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateMessageMentionRead(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateMessageMentionRead>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateMessageSendAcknowledged(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateMessageSendAcknowledged>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateMessageSendFailed(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateMessageSendFailed>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateMessageSendSucceeded(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateMessageSendSucceeded>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateMessageUnreadReactions(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateMessageUnreadReactions>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateNewCallSignalingData(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateNewCallSignalingData>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateNewCallbackQuery(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateNewCallbackQuery>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateNewChat(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateNewChat>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateNewChatJoinRequest(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateNewChatJoinRequest>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateNewChosenInlineResult(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateNewChosenInlineResult>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateNewCustomEvent(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateNewCustomEvent>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateNewCustomQuery(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateNewCustomQuery>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateNewInlineCallbackQuery(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateNewInlineCallbackQuery>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateNewInlineQuery(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateNewInlineQuery>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateNewMessage(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateNewMessage>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateNewPreCheckoutQuery(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateNewPreCheckoutQuery>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateNewShippingQuery(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateNewShippingQuery>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateNotification(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateNotification>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateNotificationGroup(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateNotificationGroup>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateOption(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateOption>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdatePoll(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdatePoll>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdatePollAnswer(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdatePollAnswer>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateRecentStickers(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateRecentStickers>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateSavedAnimations(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateSavedAnimations>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateSavedNotificationSounds(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateSavedNotificationSounds>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateScopeNotificationSettings(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateScopeNotificationSettings>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateSecretChat(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateSecretChat>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateSelectedBackground(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateSelectedBackground>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateServiceNotification(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateServiceNotification>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateStickerSet(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateStickerSet>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateSuggestedActions(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateSuggestedActions>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateSupergroup(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateSupergroup>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateSupergroupFullInfo(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateSupergroupFullInfo>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateTermsOfService(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateTermsOfService>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateTrendingStickerSets(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateTrendingStickerSets>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateUnreadChatCount(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateUnreadChatCount>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateUnreadMessageCount(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateUnreadMessageCount>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateUser(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateUser>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateUserFullInfo(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateUserFullInfo>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateUserPrivacySettingRules(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateUserPrivacySettingRules>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateUserStatus(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateUserStatus>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateUsersNearby(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateUsersNearby>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)

@ScenarioDsl
@StateDeclaration
fun TdScenarioRootBuilder.onUpdateWebAppMessageSent(
    vararg conditions: OnlyIf,
    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateWebAppMessageSent>, TdReactions>.() -> Unit
) = onUpdate(conditions = conditions, body = body)
