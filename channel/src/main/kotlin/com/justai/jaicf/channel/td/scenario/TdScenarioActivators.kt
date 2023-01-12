package com.justai.jaicf.channel.td.scenario

import com.justai.jaicf.builder.ActivationRulesBuilder
import it.tdlight.jni.TdApi

fun ActivationRulesBuilder.tdAnimatedEmojiMessage() = tdMessage<TdApi.MessageAnimatedEmoji>()

fun ActivationRulesBuilder.tdAnimationMessage() = tdMessage<TdApi.MessageAnimation>()

fun ActivationRulesBuilder.tdAudioMessage() = tdMessage<TdApi.MessageAudio>()

fun ActivationRulesBuilder.tdBasicGroupChatCreateMessage() = tdMessage<TdApi.MessageBasicGroupChatCreate>()

fun ActivationRulesBuilder.tdBotWriteAccessAllowedMessage() = tdMessage<TdApi.MessageBotWriteAccessAllowed>()

fun ActivationRulesBuilder.tdCallMessage() = tdMessage<TdApi.MessageCall>()

fun ActivationRulesBuilder.tdChatAddMembersMessage() = tdMessage<TdApi.MessageChatAddMembers>()

fun ActivationRulesBuilder.tdChatChangePhotoMessage() = tdMessage<TdApi.MessageChatChangePhoto>()

fun ActivationRulesBuilder.tdChatChangeTitleMessage() = tdMessage<TdApi.MessageChatChangeTitle>()

fun ActivationRulesBuilder.tdChatDeleteMemberMessage() = tdMessage<TdApi.MessageChatDeleteMember>()

fun ActivationRulesBuilder.tdChatDeletePhotoMessage() = tdMessage<TdApi.MessageChatDeletePhoto>()

fun ActivationRulesBuilder.tdChatJoinByLinkMessage() = tdMessage<TdApi.MessageChatJoinByLink>()

fun ActivationRulesBuilder.tdChatJoinByRequestMessage() = tdMessage<TdApi.MessageChatJoinByRequest>()

fun ActivationRulesBuilder.tdChatSetMessageAutoDeleteTimeMessage() = tdMessage<TdApi.MessageChatSetMessageAutoDeleteTime>()

fun ActivationRulesBuilder.tdChatSetThemeMessage() = tdMessage<TdApi.MessageChatSetTheme>()

fun ActivationRulesBuilder.tdChatUpgradeFromMessage() = tdMessage<TdApi.MessageChatUpgradeFrom>()

fun ActivationRulesBuilder.tdChatUpgradeToMessage() = tdMessage<TdApi.MessageChatUpgradeTo>()

fun ActivationRulesBuilder.tdContactMessage() = tdMessage<TdApi.MessageContact>()

fun ActivationRulesBuilder.tdContactRegisteredMessage() = tdMessage<TdApi.MessageContactRegistered>()

fun ActivationRulesBuilder.tdCustomServiceActionMessage() = tdMessage<TdApi.MessageCustomServiceAction>()

fun ActivationRulesBuilder.tdDiceMessage() = tdMessage<TdApi.MessageDice>()

fun ActivationRulesBuilder.tdDocumentMessage() = tdMessage<TdApi.MessageDocument>()

fun ActivationRulesBuilder.tdExpiredPhotoMessage() = tdMessage<TdApi.MessageExpiredPhoto>()

fun ActivationRulesBuilder.tdExpiredVideoMessage() = tdMessage<TdApi.MessageExpiredVideo>()

fun ActivationRulesBuilder.tdForumTopicCreatedMessage() = tdMessage<TdApi.MessageForumTopicCreated>()

fun ActivationRulesBuilder.tdForumTopicEditedMessage() = tdMessage<TdApi.MessageForumTopicEdited>()

fun ActivationRulesBuilder.tdForumTopicIsClosedToggledMessage() = tdMessage<TdApi.MessageForumTopicIsClosedToggled>()

fun ActivationRulesBuilder.tdForumTopicIsHiddenToggledMessage() = tdMessage<TdApi.MessageForumTopicIsHiddenToggled>()

fun ActivationRulesBuilder.tdGameMessage() = tdMessage<TdApi.MessageGame>()

fun ActivationRulesBuilder.tdGameScoreMessage() = tdMessage<TdApi.MessageGameScore>()

fun ActivationRulesBuilder.tdGiftedPremiumMessage() = tdMessage<TdApi.MessageGiftedPremium>()

fun ActivationRulesBuilder.tdInviteVideoChatParticipantsMessage() = tdMessage<TdApi.MessageInviteVideoChatParticipants>()

fun ActivationRulesBuilder.tdInvoiceMessage() = tdMessage<TdApi.MessageInvoice>()

fun ActivationRulesBuilder.tdLocationMessage() = tdMessage<TdApi.MessageLocation>()

fun ActivationRulesBuilder.tdPassportDataReceivedMessage() = tdMessage<TdApi.MessagePassportDataReceived>()

fun ActivationRulesBuilder.tdPassportDataSentMessage() = tdMessage<TdApi.MessagePassportDataSent>()

fun ActivationRulesBuilder.tdPaymentSuccessfulMessage() = tdMessage<TdApi.MessagePaymentSuccessful>()

fun ActivationRulesBuilder.tdPaymentSuccessfulBotMessage() = tdMessage<TdApi.MessagePaymentSuccessfulBot>()

fun ActivationRulesBuilder.tdPhotoMessage() = tdMessage<TdApi.MessagePhoto>()

fun ActivationRulesBuilder.tdPinMessageMessage() = tdMessage<TdApi.MessagePinMessage>()

fun ActivationRulesBuilder.tdPollMessage() = tdMessage<TdApi.MessagePoll>()

fun ActivationRulesBuilder.tdProximityAlertTriggeredMessage() = tdMessage<TdApi.MessageProximityAlertTriggered>()

fun ActivationRulesBuilder.tdScreenshotTakenMessage() = tdMessage<TdApi.MessageScreenshotTaken>()

fun ActivationRulesBuilder.tdStickerMessage() = tdMessage<TdApi.MessageSticker>()

fun ActivationRulesBuilder.tdSuggestProfilePhotoMessage() = tdMessage<TdApi.MessageSuggestProfilePhoto>()

fun ActivationRulesBuilder.tdSupergroupChatCreateMessage() = tdMessage<TdApi.MessageSupergroupChatCreate>()

fun ActivationRulesBuilder.tdTextMessage() = tdMessage<TdApi.MessageText>()

fun ActivationRulesBuilder.tdUnsupportedMessage() = tdMessage<TdApi.MessageUnsupported>()

fun ActivationRulesBuilder.tdVenueMessage() = tdMessage<TdApi.MessageVenue>()

fun ActivationRulesBuilder.tdVideoMessage() = tdMessage<TdApi.MessageVideo>()

fun ActivationRulesBuilder.tdVideoChatEndedMessage() = tdMessage<TdApi.MessageVideoChatEnded>()

fun ActivationRulesBuilder.tdVideoChatScheduledMessage() = tdMessage<TdApi.MessageVideoChatScheduled>()

fun ActivationRulesBuilder.tdVideoChatStartedMessage() = tdMessage<TdApi.MessageVideoChatStarted>()

fun ActivationRulesBuilder.tdVideoNoteMessage() = tdMessage<TdApi.MessageVideoNote>()

fun ActivationRulesBuilder.tdVoiceNoteMessage() = tdMessage<TdApi.MessageVoiceNote>()

fun ActivationRulesBuilder.tdWebAppDataReceivedMessage() = tdMessage<TdApi.MessageWebAppDataReceived>()

fun ActivationRulesBuilder.tdWebAppDataSentMessage() = tdMessage<TdApi.MessageWebAppDataSent>()

fun ActivationRulesBuilder.tdWebsiteConnectedMessage() = tdMessage<TdApi.MessageWebsiteConnected>()

fun ActivationRulesBuilder.tdUpdateAccessHash() = tdUpdate<TdApi.UpdateAccessHash>()

fun ActivationRulesBuilder.tdUpdateActiveEmojiReactions() = tdUpdate<TdApi.UpdateActiveEmojiReactions>()

fun ActivationRulesBuilder.tdUpdateActiveNotifications() = tdUpdate<TdApi.UpdateActiveNotifications>()

fun ActivationRulesBuilder.tdUpdateAnimatedEmojiMessageClicked() = tdUpdate<TdApi.UpdateAnimatedEmojiMessageClicked>()

fun ActivationRulesBuilder.tdUpdateAnimationSearchParameters() = tdUpdate<TdApi.UpdateAnimationSearchParameters>()

fun ActivationRulesBuilder.tdUpdateAttachmentMenuBots() = tdUpdate<TdApi.UpdateAttachmentMenuBots>()

fun ActivationRulesBuilder.tdUpdateAuthorizationState() = tdUpdate<TdApi.UpdateAuthorizationState>()

fun ActivationRulesBuilder.tdUpdateBasicGroup() = tdUpdate<TdApi.UpdateBasicGroup>()

fun ActivationRulesBuilder.tdUpdateBasicGroupFullInfo() = tdUpdate<TdApi.UpdateBasicGroupFullInfo>()

fun ActivationRulesBuilder.tdUpdateCall() = tdUpdate<TdApi.UpdateCall>()

fun ActivationRulesBuilder.tdUpdateChatAction() = tdUpdate<TdApi.UpdateChatAction>()

fun ActivationRulesBuilder.tdUpdateChatActionBar() = tdUpdate<TdApi.UpdateChatActionBar>()

fun ActivationRulesBuilder.tdUpdateChatAvailableReactions() = tdUpdate<TdApi.UpdateChatAvailableReactions>()

fun ActivationRulesBuilder.tdUpdateChatDefaultDisableNotification() = tdUpdate<TdApi.UpdateChatDefaultDisableNotification>()

fun ActivationRulesBuilder.tdUpdateChatDraftMessage() = tdUpdate<TdApi.UpdateChatDraftMessage>()

fun ActivationRulesBuilder.tdUpdateChatFilters() = tdUpdate<TdApi.UpdateChatFilters>()

fun ActivationRulesBuilder.tdUpdateChatHasProtectedContent() = tdUpdate<TdApi.UpdateChatHasProtectedContent>()

fun ActivationRulesBuilder.tdUpdateChatHasScheduledMessages() = tdUpdate<TdApi.UpdateChatHasScheduledMessages>()

fun ActivationRulesBuilder.tdUpdateChatIsBlocked() = tdUpdate<TdApi.UpdateChatIsBlocked>()

fun ActivationRulesBuilder.tdUpdateChatIsMarkedAsUnread() = tdUpdate<TdApi.UpdateChatIsMarkedAsUnread>()

fun ActivationRulesBuilder.tdUpdateChatLastMessage() = tdUpdate<TdApi.UpdateChatLastMessage>()

fun ActivationRulesBuilder.tdUpdateChatMember() = tdUpdate<TdApi.UpdateChatMember>()

fun ActivationRulesBuilder.tdUpdateChatMessageAutoDeleteTime() = tdUpdate<TdApi.UpdateChatMessageAutoDeleteTime>()

fun ActivationRulesBuilder.tdUpdateChatMessageSender() = tdUpdate<TdApi.UpdateChatMessageSender>()

fun ActivationRulesBuilder.tdUpdateChatNotificationSettings() = tdUpdate<TdApi.UpdateChatNotificationSettings>()

fun ActivationRulesBuilder.tdUpdateChatOnlineMemberCount() = tdUpdate<TdApi.UpdateChatOnlineMemberCount>()

fun ActivationRulesBuilder.tdUpdateChatPendingJoinRequests() = tdUpdate<TdApi.UpdateChatPendingJoinRequests>()

fun ActivationRulesBuilder.tdUpdateChatPermissions() = tdUpdate<TdApi.UpdateChatPermissions>()

fun ActivationRulesBuilder.tdUpdateChatPhoto() = tdUpdate<TdApi.UpdateChatPhoto>()

fun ActivationRulesBuilder.tdUpdateChatPosition() = tdUpdate<TdApi.UpdateChatPosition>()

fun ActivationRulesBuilder.tdUpdateChatReadInbox() = tdUpdate<TdApi.UpdateChatReadInbox>()

fun ActivationRulesBuilder.tdUpdateChatReadOutbox() = tdUpdate<TdApi.UpdateChatReadOutbox>()

fun ActivationRulesBuilder.tdUpdateChatReplyMarkup() = tdUpdate<TdApi.UpdateChatReplyMarkup>()

fun ActivationRulesBuilder.tdUpdateChatTheme() = tdUpdate<TdApi.UpdateChatTheme>()

fun ActivationRulesBuilder.tdUpdateChatThemes() = tdUpdate<TdApi.UpdateChatThemes>()

fun ActivationRulesBuilder.tdUpdateChatTitle() = tdUpdate<TdApi.UpdateChatTitle>()

fun ActivationRulesBuilder.tdUpdateChatUnreadMentionCount() = tdUpdate<TdApi.UpdateChatUnreadMentionCount>()

fun ActivationRulesBuilder.tdUpdateChatUnreadReactionCount() = tdUpdate<TdApi.UpdateChatUnreadReactionCount>()

fun ActivationRulesBuilder.tdUpdateChatVideoChat() = tdUpdate<TdApi.UpdateChatVideoChat>()

fun ActivationRulesBuilder.tdUpdateConnectionState() = tdUpdate<TdApi.UpdateConnectionState>()

fun ActivationRulesBuilder.tdUpdateDefaultReactionType() = tdUpdate<TdApi.UpdateDefaultReactionType>()

fun ActivationRulesBuilder.tdUpdateDeleteMessages() = tdUpdate<TdApi.UpdateDeleteMessages>()

fun ActivationRulesBuilder.tdUpdateDiceEmojis() = tdUpdate<TdApi.UpdateDiceEmojis>()

fun ActivationRulesBuilder.tdUpdateFavoriteStickers() = tdUpdate<TdApi.UpdateFavoriteStickers>()

fun ActivationRulesBuilder.tdUpdateFile() = tdUpdate<TdApi.UpdateFile>()

fun ActivationRulesBuilder.tdUpdateFileAddedToDownloads() = tdUpdate<TdApi.UpdateFileAddedToDownloads>()

fun ActivationRulesBuilder.tdUpdateFileDownload() = tdUpdate<TdApi.UpdateFileDownload>()

fun ActivationRulesBuilder.tdUpdateFileDownloads() = tdUpdate<TdApi.UpdateFileDownloads>()

fun ActivationRulesBuilder.tdUpdateFileGenerationStart() = tdUpdate<TdApi.UpdateFileGenerationStart>()

fun ActivationRulesBuilder.tdUpdateFileGenerationStop() = tdUpdate<TdApi.UpdateFileGenerationStop>()

fun ActivationRulesBuilder.tdUpdateFileRemovedFromDownloads() = tdUpdate<TdApi.UpdateFileRemovedFromDownloads>()

fun ActivationRulesBuilder.tdUpdateForumTopicInfo() = tdUpdate<TdApi.UpdateForumTopicInfo>()

fun ActivationRulesBuilder.tdUpdateGroupCall() = tdUpdate<TdApi.UpdateGroupCall>()

fun ActivationRulesBuilder.tdUpdateGroupCallParticipant() = tdUpdate<TdApi.UpdateGroupCallParticipant>()

fun ActivationRulesBuilder.tdUpdateHavePendingNotifications() = tdUpdate<TdApi.UpdateHavePendingNotifications>()

fun ActivationRulesBuilder.tdUpdateInstalledStickerSets() = tdUpdate<TdApi.UpdateInstalledStickerSets>()

fun ActivationRulesBuilder.tdUpdateLanguagePackStrings() = tdUpdate<TdApi.UpdateLanguagePackStrings>()

fun ActivationRulesBuilder.tdUpdateMessageContent() = tdUpdate<TdApi.UpdateMessageContent>()

fun ActivationRulesBuilder.tdUpdateMessageContentOpened() = tdUpdate<TdApi.UpdateMessageContentOpened>()

fun ActivationRulesBuilder.tdUpdateMessageEdited() = tdUpdate<TdApi.UpdateMessageEdited>()

fun ActivationRulesBuilder.tdUpdateMessageInteractionInfo() = tdUpdate<TdApi.UpdateMessageInteractionInfo>()

fun ActivationRulesBuilder.tdUpdateMessageIsPinned() = tdUpdate<TdApi.UpdateMessageIsPinned>()

fun ActivationRulesBuilder.tdUpdateMessageLiveLocationViewed() = tdUpdate<TdApi.UpdateMessageLiveLocationViewed>()

fun ActivationRulesBuilder.tdUpdateMessageMentionRead() = tdUpdate<TdApi.UpdateMessageMentionRead>()

fun ActivationRulesBuilder.tdUpdateMessageSendAcknowledged() = tdUpdate<TdApi.UpdateMessageSendAcknowledged>()

fun ActivationRulesBuilder.tdUpdateMessageSendFailed() = tdUpdate<TdApi.UpdateMessageSendFailed>()

fun ActivationRulesBuilder.tdUpdateMessageSendSucceeded() = tdUpdate<TdApi.UpdateMessageSendSucceeded>()

fun ActivationRulesBuilder.tdUpdateMessageUnreadReactions() = tdUpdate<TdApi.UpdateMessageUnreadReactions>()

fun ActivationRulesBuilder.tdUpdateNewCallSignalingData() = tdUpdate<TdApi.UpdateNewCallSignalingData>()

fun ActivationRulesBuilder.tdUpdateNewCallbackQuery() = tdUpdate<TdApi.UpdateNewCallbackQuery>()

fun ActivationRulesBuilder.tdUpdateNewChat() = tdUpdate<TdApi.UpdateNewChat>()

fun ActivationRulesBuilder.tdUpdateNewChatJoinRequest() = tdUpdate<TdApi.UpdateNewChatJoinRequest>()

fun ActivationRulesBuilder.tdUpdateNewChosenInlineResult() = tdUpdate<TdApi.UpdateNewChosenInlineResult>()

fun ActivationRulesBuilder.tdUpdateNewCustomEvent() = tdUpdate<TdApi.UpdateNewCustomEvent>()

fun ActivationRulesBuilder.tdUpdateNewCustomQuery() = tdUpdate<TdApi.UpdateNewCustomQuery>()

fun ActivationRulesBuilder.tdUpdateNewInlineCallbackQuery() = tdUpdate<TdApi.UpdateNewInlineCallbackQuery>()

fun ActivationRulesBuilder.tdUpdateNewInlineQuery() = tdUpdate<TdApi.UpdateNewInlineQuery>()

fun ActivationRulesBuilder.tdUpdateNewMessage() = tdUpdate<TdApi.UpdateNewMessage>()

fun ActivationRulesBuilder.tdUpdateNewPreCheckoutQuery() = tdUpdate<TdApi.UpdateNewPreCheckoutQuery>()

fun ActivationRulesBuilder.tdUpdateNewShippingQuery() = tdUpdate<TdApi.UpdateNewShippingQuery>()

fun ActivationRulesBuilder.tdUpdateNotification() = tdUpdate<TdApi.UpdateNotification>()

fun ActivationRulesBuilder.tdUpdateNotificationGroup() = tdUpdate<TdApi.UpdateNotificationGroup>()

fun ActivationRulesBuilder.tdUpdateOption() = tdUpdate<TdApi.UpdateOption>()

fun ActivationRulesBuilder.tdUpdatePoll() = tdUpdate<TdApi.UpdatePoll>()

fun ActivationRulesBuilder.tdUpdatePollAnswer() = tdUpdate<TdApi.UpdatePollAnswer>()

fun ActivationRulesBuilder.tdUpdateRecentStickers() = tdUpdate<TdApi.UpdateRecentStickers>()

fun ActivationRulesBuilder.tdUpdateSavedAnimations() = tdUpdate<TdApi.UpdateSavedAnimations>()

fun ActivationRulesBuilder.tdUpdateSavedNotificationSounds() = tdUpdate<TdApi.UpdateSavedNotificationSounds>()

fun ActivationRulesBuilder.tdUpdateScopeNotificationSettings() = tdUpdate<TdApi.UpdateScopeNotificationSettings>()

fun ActivationRulesBuilder.tdUpdateSecretChat() = tdUpdate<TdApi.UpdateSecretChat>()

fun ActivationRulesBuilder.tdUpdateSelectedBackground() = tdUpdate<TdApi.UpdateSelectedBackground>()

fun ActivationRulesBuilder.tdUpdateServiceNotification() = tdUpdate<TdApi.UpdateServiceNotification>()

fun ActivationRulesBuilder.tdUpdateStickerSet() = tdUpdate<TdApi.UpdateStickerSet>()

fun ActivationRulesBuilder.tdUpdateSuggestedActions() = tdUpdate<TdApi.UpdateSuggestedActions>()

fun ActivationRulesBuilder.tdUpdateSupergroup() = tdUpdate<TdApi.UpdateSupergroup>()

fun ActivationRulesBuilder.tdUpdateSupergroupFullInfo() = tdUpdate<TdApi.UpdateSupergroupFullInfo>()

fun ActivationRulesBuilder.tdUpdateTermsOfService() = tdUpdate<TdApi.UpdateTermsOfService>()

fun ActivationRulesBuilder.tdUpdateTrendingStickerSets() = tdUpdate<TdApi.UpdateTrendingStickerSets>()

fun ActivationRulesBuilder.tdUpdateUnreadChatCount() = tdUpdate<TdApi.UpdateUnreadChatCount>()

fun ActivationRulesBuilder.tdUpdateUnreadMessageCount() = tdUpdate<TdApi.UpdateUnreadMessageCount>()

fun ActivationRulesBuilder.tdUpdateUser() = tdUpdate<TdApi.UpdateUser>()

fun ActivationRulesBuilder.tdUpdateUserFullInfo() = tdUpdate<TdApi.UpdateUserFullInfo>()

fun ActivationRulesBuilder.tdUpdateUserPrivacySettingRules() = tdUpdate<TdApi.UpdateUserPrivacySettingRules>()

fun ActivationRulesBuilder.tdUpdateUserStatus() = tdUpdate<TdApi.UpdateUserStatus>()

fun ActivationRulesBuilder.tdUpdateUsersNearby() = tdUpdate<TdApi.UpdateUsersNearby>()

fun ActivationRulesBuilder.tdUpdateWebAppMessageSent() = tdUpdate<TdApi.UpdateWebAppMessageSent>()
