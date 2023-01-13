package com.justai.jaicf.channel.td.scenario

import com.justai.jaicf.channel.td.tdMessageType
import com.justai.jaicf.channel.td.tdUpdateType
import it.tdlight.jni.TdApi

val tdAnimatedEmojiMessageType = tdMessageType<TdApi.MessageAnimatedEmoji>()

val tdAnimationMessageType = tdMessageType<TdApi.MessageAnimation>()

val tdAudioMessageType = tdMessageType<TdApi.MessageAudio>()

val tdBasicGroupChatCreateMessageType = tdMessageType<TdApi.MessageBasicGroupChatCreate>()

val tdBotWriteAccessAllowedMessageType = tdMessageType<TdApi.MessageBotWriteAccessAllowed>()

val tdCallMessageType = tdMessageType<TdApi.MessageCall>()

val tdChatAddMembersMessageType = tdMessageType<TdApi.MessageChatAddMembers>()

val tdChatChangePhotoMessageType = tdMessageType<TdApi.MessageChatChangePhoto>()

val tdChatChangeTitleMessageType = tdMessageType<TdApi.MessageChatChangeTitle>()

val tdChatDeleteMemberMessageType = tdMessageType<TdApi.MessageChatDeleteMember>()

val tdChatDeletePhotoMessageType = tdMessageType<TdApi.MessageChatDeletePhoto>()

val tdChatJoinByLinkMessageType = tdMessageType<TdApi.MessageChatJoinByLink>()

val tdChatJoinByRequestMessageType = tdMessageType<TdApi.MessageChatJoinByRequest>()

val tdChatSetMessageAutoDeleteTimeMessageType = tdMessageType<TdApi.MessageChatSetMessageAutoDeleteTime>()

val tdChatSetThemeMessageType = tdMessageType<TdApi.MessageChatSetTheme>()

val tdChatUpgradeFromMessageType = tdMessageType<TdApi.MessageChatUpgradeFrom>()

val tdChatUpgradeToMessageType = tdMessageType<TdApi.MessageChatUpgradeTo>()

val tdContactMessageType = tdMessageType<TdApi.MessageContact>()

val tdContactRegisteredMessageType = tdMessageType<TdApi.MessageContactRegistered>()

val tdCustomServiceActionMessageType = tdMessageType<TdApi.MessageCustomServiceAction>()

val tdDiceMessageType = tdMessageType<TdApi.MessageDice>()

val tdDocumentMessageType = tdMessageType<TdApi.MessageDocument>()

val tdExpiredPhotoMessageType = tdMessageType<TdApi.MessageExpiredPhoto>()

val tdExpiredVideoMessageType = tdMessageType<TdApi.MessageExpiredVideo>()

val tdForumTopicCreatedMessageType = tdMessageType<TdApi.MessageForumTopicCreated>()

val tdForumTopicEditedMessageType = tdMessageType<TdApi.MessageForumTopicEdited>()

val tdForumTopicIsClosedToggledMessageType = tdMessageType<TdApi.MessageForumTopicIsClosedToggled>()

val tdForumTopicIsHiddenToggledMessageType = tdMessageType<TdApi.MessageForumTopicIsHiddenToggled>()

val tdGameMessageType = tdMessageType<TdApi.MessageGame>()

val tdGameScoreMessageType = tdMessageType<TdApi.MessageGameScore>()

val tdGiftedPremiumMessageType = tdMessageType<TdApi.MessageGiftedPremium>()

val tdInviteVideoChatParticipantsMessageType = tdMessageType<TdApi.MessageInviteVideoChatParticipants>()

val tdInvoiceMessageType = tdMessageType<TdApi.MessageInvoice>()

val tdLocationMessageType = tdMessageType<TdApi.MessageLocation>()

val tdPassportDataReceivedMessageType = tdMessageType<TdApi.MessagePassportDataReceived>()

val tdPassportDataSentMessageType = tdMessageType<TdApi.MessagePassportDataSent>()

val tdPaymentSuccessfulMessageType = tdMessageType<TdApi.MessagePaymentSuccessful>()

val tdPaymentSuccessfulBotMessageType = tdMessageType<TdApi.MessagePaymentSuccessfulBot>()

val tdPhotoMessageType = tdMessageType<TdApi.MessagePhoto>()

val tdPinMessageMessageType = tdMessageType<TdApi.MessagePinMessage>()

val tdPollMessageType = tdMessageType<TdApi.MessagePoll>()

val tdProximityAlertTriggeredMessageType = tdMessageType<TdApi.MessageProximityAlertTriggered>()

val tdScreenshotTakenMessageType = tdMessageType<TdApi.MessageScreenshotTaken>()

val tdStickerMessageType = tdMessageType<TdApi.MessageSticker>()

val tdSuggestProfilePhotoMessageType = tdMessageType<TdApi.MessageSuggestProfilePhoto>()

val tdSupergroupChatCreateMessageType = tdMessageType<TdApi.MessageSupergroupChatCreate>()

val tdTextMessageType = tdMessageType<TdApi.MessageText>()

val tdUnsupportedMessageType = tdMessageType<TdApi.MessageUnsupported>()

val tdVenueMessageType = tdMessageType<TdApi.MessageVenue>()

val tdVideoMessageType = tdMessageType<TdApi.MessageVideo>()

val tdVideoChatEndedMessageType = tdMessageType<TdApi.MessageVideoChatEnded>()

val tdVideoChatScheduledMessageType = tdMessageType<TdApi.MessageVideoChatScheduled>()

val tdVideoChatStartedMessageType = tdMessageType<TdApi.MessageVideoChatStarted>()

val tdVideoNoteMessageType = tdMessageType<TdApi.MessageVideoNote>()

val tdVoiceNoteMessageType = tdMessageType<TdApi.MessageVoiceNote>()

val tdWebAppDataReceivedMessageType = tdMessageType<TdApi.MessageWebAppDataReceived>()

val tdWebAppDataSentMessageType = tdMessageType<TdApi.MessageWebAppDataSent>()

val tdWebsiteConnectedMessageType = tdMessageType<TdApi.MessageWebsiteConnected>()

val tdUpdateAccessHashType = tdUpdateType<TdApi.UpdateAccessHash>()

val tdUpdateActiveEmojiReactionsType = tdUpdateType<TdApi.UpdateActiveEmojiReactions>()

val tdUpdateActiveNotificationsType = tdUpdateType<TdApi.UpdateActiveNotifications>()

val tdUpdateAnimatedEmojiMessageClickedType = tdUpdateType<TdApi.UpdateAnimatedEmojiMessageClicked>()

val tdUpdateAnimationSearchParametersType = tdUpdateType<TdApi.UpdateAnimationSearchParameters>()

val tdUpdateAttachmentMenuBotsType = tdUpdateType<TdApi.UpdateAttachmentMenuBots>()

val tdUpdateAuthorizationStateType = tdUpdateType<TdApi.UpdateAuthorizationState>()

val tdUpdateBasicGroupType = tdUpdateType<TdApi.UpdateBasicGroup>()

val tdUpdateBasicGroupFullInfoType = tdUpdateType<TdApi.UpdateBasicGroupFullInfo>()

val tdUpdateCallType = tdUpdateType<TdApi.UpdateCall>()

val tdUpdateChatActionType = tdUpdateType<TdApi.UpdateChatAction>()

val tdUpdateChatActionBarType = tdUpdateType<TdApi.UpdateChatActionBar>()

val tdUpdateChatAvailableReactionsType = tdUpdateType<TdApi.UpdateChatAvailableReactions>()

val tdUpdateChatDefaultDisableNotificationType = tdUpdateType<TdApi.UpdateChatDefaultDisableNotification>()

val tdUpdateChatDraftMessageType = tdUpdateType<TdApi.UpdateChatDraftMessage>()

val tdUpdateChatFiltersType = tdUpdateType<TdApi.UpdateChatFilters>()

val tdUpdateChatHasProtectedContentType = tdUpdateType<TdApi.UpdateChatHasProtectedContent>()

val tdUpdateChatHasScheduledMessagesType = tdUpdateType<TdApi.UpdateChatHasScheduledMessages>()

val tdUpdateChatIsBlockedType = tdUpdateType<TdApi.UpdateChatIsBlocked>()

val tdUpdateChatIsMarkedAsUnreadType = tdUpdateType<TdApi.UpdateChatIsMarkedAsUnread>()

val tdUpdateChatLastMessageType = tdUpdateType<TdApi.UpdateChatLastMessage>()

val tdUpdateChatMemberType = tdUpdateType<TdApi.UpdateChatMember>()

val tdUpdateChatMessageAutoDeleteTimeType = tdUpdateType<TdApi.UpdateChatMessageAutoDeleteTime>()

val tdUpdateChatMessageSenderType = tdUpdateType<TdApi.UpdateChatMessageSender>()

val tdUpdateChatNotificationSettingsType = tdUpdateType<TdApi.UpdateChatNotificationSettings>()

val tdUpdateChatOnlineMemberCountType = tdUpdateType<TdApi.UpdateChatOnlineMemberCount>()

val tdUpdateChatPendingJoinRequestsType = tdUpdateType<TdApi.UpdateChatPendingJoinRequests>()

val tdUpdateChatPermissionsType = tdUpdateType<TdApi.UpdateChatPermissions>()

val tdUpdateChatPhotoType = tdUpdateType<TdApi.UpdateChatPhoto>()

val tdUpdateChatPositionType = tdUpdateType<TdApi.UpdateChatPosition>()

val tdUpdateChatReadInboxType = tdUpdateType<TdApi.UpdateChatReadInbox>()

val tdUpdateChatReadOutboxType = tdUpdateType<TdApi.UpdateChatReadOutbox>()

val tdUpdateChatReplyMarkupType = tdUpdateType<TdApi.UpdateChatReplyMarkup>()

val tdUpdateChatThemeType = tdUpdateType<TdApi.UpdateChatTheme>()

val tdUpdateChatThemesType = tdUpdateType<TdApi.UpdateChatThemes>()

val tdUpdateChatTitleType = tdUpdateType<TdApi.UpdateChatTitle>()

val tdUpdateChatUnreadMentionCountType = tdUpdateType<TdApi.UpdateChatUnreadMentionCount>()

val tdUpdateChatUnreadReactionCountType = tdUpdateType<TdApi.UpdateChatUnreadReactionCount>()

val tdUpdateChatVideoChatType = tdUpdateType<TdApi.UpdateChatVideoChat>()

val tdUpdateConnectionStateType = tdUpdateType<TdApi.UpdateConnectionState>()

val tdUpdateDefaultReactionTypeType = tdUpdateType<TdApi.UpdateDefaultReactionType>()

val tdUpdateDeleteMessagesType = tdUpdateType<TdApi.UpdateDeleteMessages>()

val tdUpdateDiceEmojisType = tdUpdateType<TdApi.UpdateDiceEmojis>()

val tdUpdateFavoriteStickersType = tdUpdateType<TdApi.UpdateFavoriteStickers>()

val tdUpdateFileType = tdUpdateType<TdApi.UpdateFile>()

val tdUpdateFileAddedToDownloadsType = tdUpdateType<TdApi.UpdateFileAddedToDownloads>()

val tdUpdateFileDownloadType = tdUpdateType<TdApi.UpdateFileDownload>()

val tdUpdateFileDownloadsType = tdUpdateType<TdApi.UpdateFileDownloads>()

val tdUpdateFileGenerationStartType = tdUpdateType<TdApi.UpdateFileGenerationStart>()

val tdUpdateFileGenerationStopType = tdUpdateType<TdApi.UpdateFileGenerationStop>()

val tdUpdateFileRemovedFromDownloadsType = tdUpdateType<TdApi.UpdateFileRemovedFromDownloads>()

val tdUpdateForumTopicInfoType = tdUpdateType<TdApi.UpdateForumTopicInfo>()

val tdUpdateGroupCallType = tdUpdateType<TdApi.UpdateGroupCall>()

val tdUpdateGroupCallParticipantType = tdUpdateType<TdApi.UpdateGroupCallParticipant>()

val tdUpdateHavePendingNotificationsType = tdUpdateType<TdApi.UpdateHavePendingNotifications>()

val tdUpdateInstalledStickerSetsType = tdUpdateType<TdApi.UpdateInstalledStickerSets>()

val tdUpdateLanguagePackStringsType = tdUpdateType<TdApi.UpdateLanguagePackStrings>()

val tdUpdateMessageContentType = tdUpdateType<TdApi.UpdateMessageContent>()

val tdUpdateMessageContentOpenedType = tdUpdateType<TdApi.UpdateMessageContentOpened>()

val tdUpdateMessageEditedType = tdUpdateType<TdApi.UpdateMessageEdited>()

val tdUpdateMessageInteractionInfoType = tdUpdateType<TdApi.UpdateMessageInteractionInfo>()

val tdUpdateMessageIsPinnedType = tdUpdateType<TdApi.UpdateMessageIsPinned>()

val tdUpdateMessageLiveLocationViewedType = tdUpdateType<TdApi.UpdateMessageLiveLocationViewed>()

val tdUpdateMessageMentionReadType = tdUpdateType<TdApi.UpdateMessageMentionRead>()

val tdUpdateMessageSendAcknowledgedType = tdUpdateType<TdApi.UpdateMessageSendAcknowledged>()

val tdUpdateMessageSendFailedType = tdUpdateType<TdApi.UpdateMessageSendFailed>()

val tdUpdateMessageSendSucceededType = tdUpdateType<TdApi.UpdateMessageSendSucceeded>()

val tdUpdateMessageUnreadReactionsType = tdUpdateType<TdApi.UpdateMessageUnreadReactions>()

val tdUpdateNewCallSignalingDataType = tdUpdateType<TdApi.UpdateNewCallSignalingData>()

val tdUpdateNewCallbackQueryType = tdUpdateType<TdApi.UpdateNewCallbackQuery>()

val tdUpdateNewChatType = tdUpdateType<TdApi.UpdateNewChat>()

val tdUpdateNewChatJoinRequestType = tdUpdateType<TdApi.UpdateNewChatJoinRequest>()

val tdUpdateNewChosenInlineResultType = tdUpdateType<TdApi.UpdateNewChosenInlineResult>()

val tdUpdateNewCustomEventType = tdUpdateType<TdApi.UpdateNewCustomEvent>()

val tdUpdateNewCustomQueryType = tdUpdateType<TdApi.UpdateNewCustomQuery>()

val tdUpdateNewInlineCallbackQueryType = tdUpdateType<TdApi.UpdateNewInlineCallbackQuery>()

val tdUpdateNewInlineQueryType = tdUpdateType<TdApi.UpdateNewInlineQuery>()

val tdUpdateNewMessageType = tdUpdateType<TdApi.UpdateNewMessage>()

val tdUpdateNewPreCheckoutQueryType = tdUpdateType<TdApi.UpdateNewPreCheckoutQuery>()

val tdUpdateNewShippingQueryType = tdUpdateType<TdApi.UpdateNewShippingQuery>()

val tdUpdateNotificationType = tdUpdateType<TdApi.UpdateNotification>()

val tdUpdateNotificationGroupType = tdUpdateType<TdApi.UpdateNotificationGroup>()

val tdUpdateOptionType = tdUpdateType<TdApi.UpdateOption>()

val tdUpdatePollType = tdUpdateType<TdApi.UpdatePoll>()

val tdUpdatePollAnswerType = tdUpdateType<TdApi.UpdatePollAnswer>()

val tdUpdateRecentStickersType = tdUpdateType<TdApi.UpdateRecentStickers>()

val tdUpdateSavedAnimationsType = tdUpdateType<TdApi.UpdateSavedAnimations>()

val tdUpdateSavedNotificationSoundsType = tdUpdateType<TdApi.UpdateSavedNotificationSounds>()

val tdUpdateScopeNotificationSettingsType = tdUpdateType<TdApi.UpdateScopeNotificationSettings>()

val tdUpdateSecretChatType = tdUpdateType<TdApi.UpdateSecretChat>()

val tdUpdateSelectedBackgroundType = tdUpdateType<TdApi.UpdateSelectedBackground>()

val tdUpdateServiceNotificationType = tdUpdateType<TdApi.UpdateServiceNotification>()

val tdUpdateStickerSetType = tdUpdateType<TdApi.UpdateStickerSet>()

val tdUpdateSuggestedActionsType = tdUpdateType<TdApi.UpdateSuggestedActions>()

val tdUpdateSupergroupType = tdUpdateType<TdApi.UpdateSupergroup>()

val tdUpdateSupergroupFullInfoType = tdUpdateType<TdApi.UpdateSupergroupFullInfo>()

val tdUpdateTermsOfServiceType = tdUpdateType<TdApi.UpdateTermsOfService>()

val tdUpdateTrendingStickerSetsType = tdUpdateType<TdApi.UpdateTrendingStickerSets>()

val tdUpdateUnreadChatCountType = tdUpdateType<TdApi.UpdateUnreadChatCount>()

val tdUpdateUnreadMessageCountType = tdUpdateType<TdApi.UpdateUnreadMessageCount>()

val tdUpdateUserType = tdUpdateType<TdApi.UpdateUser>()

val tdUpdateUserFullInfoType = tdUpdateType<TdApi.UpdateUserFullInfo>()

val tdUpdateUserPrivacySettingRulesType = tdUpdateType<TdApi.UpdateUserPrivacySettingRules>()

val tdUpdateUserStatusType = tdUpdateType<TdApi.UpdateUserStatus>()

val tdUpdateUsersNearbyType = tdUpdateType<TdApi.UpdateUsersNearby>()

val tdUpdateWebAppMessageSentType = tdUpdateType<TdApi.UpdateWebAppMessageSent>()
