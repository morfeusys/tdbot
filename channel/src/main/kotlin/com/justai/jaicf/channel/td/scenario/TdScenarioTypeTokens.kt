package com.justai.jaicf.channel.td.scenario

import com.justai.jaicf.channel.td.tdMessageToken
import com.justai.jaicf.channel.td.tdUpdateToken
import it.tdlight.jni.TdApi

val tdAnimatedEmojiMessageType = tdMessageToken<TdApi.MessageAnimatedEmoji>()

val tdAnimationMessageType = tdMessageToken<TdApi.MessageAnimation>()

val tdAudioMessageType = tdMessageToken<TdApi.MessageAudio>()

val tdBasicGroupChatCreateMessageType = tdMessageToken<TdApi.MessageBasicGroupChatCreate>()

val tdBotWriteAccessAllowedMessageType = tdMessageToken<TdApi.MessageBotWriteAccessAllowed>()

val tdCallMessageType = tdMessageToken<TdApi.MessageCall>()

val tdChatAddMembersMessageType = tdMessageToken<TdApi.MessageChatAddMembers>()

val tdChatChangePhotoMessageType = tdMessageToken<TdApi.MessageChatChangePhoto>()

val tdChatChangeTitleMessageType = tdMessageToken<TdApi.MessageChatChangeTitle>()

val tdChatDeleteMemberMessageType = tdMessageToken<TdApi.MessageChatDeleteMember>()

val tdChatDeletePhotoMessageType = tdMessageToken<TdApi.MessageChatDeletePhoto>()

val tdChatJoinByLinkMessageType = tdMessageToken<TdApi.MessageChatJoinByLink>()

val tdChatJoinByRequestMessageType = tdMessageToken<TdApi.MessageChatJoinByRequest>()

val tdChatSetMessageAutoDeleteTimeMessageType = tdMessageToken<TdApi.MessageChatSetMessageAutoDeleteTime>()

val tdChatSetThemeMessageType = tdMessageToken<TdApi.MessageChatSetTheme>()

val tdChatUpgradeFromMessageType = tdMessageToken<TdApi.MessageChatUpgradeFrom>()

val tdChatUpgradeToMessageType = tdMessageToken<TdApi.MessageChatUpgradeTo>()

val tdContactMessageType = tdMessageToken<TdApi.MessageContact>()

val tdContactRegisteredMessageType = tdMessageToken<TdApi.MessageContactRegistered>()

val tdCustomServiceActionMessageType = tdMessageToken<TdApi.MessageCustomServiceAction>()

val tdDiceMessageType = tdMessageToken<TdApi.MessageDice>()

val tdDocumentMessageType = tdMessageToken<TdApi.MessageDocument>()

val tdExpiredPhotoMessageType = tdMessageToken<TdApi.MessageExpiredPhoto>()

val tdExpiredVideoMessageType = tdMessageToken<TdApi.MessageExpiredVideo>()

val tdForumTopicCreatedMessageType = tdMessageToken<TdApi.MessageForumTopicCreated>()

val tdForumTopicEditedMessageType = tdMessageToken<TdApi.MessageForumTopicEdited>()

val tdForumTopicIsClosedToggledMessageType = tdMessageToken<TdApi.MessageForumTopicIsClosedToggled>()

val tdForumTopicIsHiddenToggledMessageType = tdMessageToken<TdApi.MessageForumTopicIsHiddenToggled>()

val tdGameMessageType = tdMessageToken<TdApi.MessageGame>()

val tdGameScoreMessageType = tdMessageToken<TdApi.MessageGameScore>()

val tdGiftedPremiumMessageType = tdMessageToken<TdApi.MessageGiftedPremium>()

val tdInviteVideoChatParticipantsMessageType = tdMessageToken<TdApi.MessageInviteVideoChatParticipants>()

val tdInvoiceMessageType = tdMessageToken<TdApi.MessageInvoice>()

val tdLocationMessageType = tdMessageToken<TdApi.MessageLocation>()

val tdPassportDataReceivedMessageType = tdMessageToken<TdApi.MessagePassportDataReceived>()

val tdPassportDataSentMessageType = tdMessageToken<TdApi.MessagePassportDataSent>()

val tdPaymentSuccessfulMessageType = tdMessageToken<TdApi.MessagePaymentSuccessful>()

val tdPaymentSuccessfulBotMessageType = tdMessageToken<TdApi.MessagePaymentSuccessfulBot>()

val tdPhotoMessageType = tdMessageToken<TdApi.MessagePhoto>()

val tdPinMessageMessageType = tdMessageToken<TdApi.MessagePinMessage>()

val tdPollMessageType = tdMessageToken<TdApi.MessagePoll>()

val tdProximityAlertTriggeredMessageType = tdMessageToken<TdApi.MessageProximityAlertTriggered>()

val tdScreenshotTakenMessageType = tdMessageToken<TdApi.MessageScreenshotTaken>()

val tdStickerMessageType = tdMessageToken<TdApi.MessageSticker>()

val tdSuggestProfilePhotoMessageType = tdMessageToken<TdApi.MessageSuggestProfilePhoto>()

val tdSupergroupChatCreateMessageType = tdMessageToken<TdApi.MessageSupergroupChatCreate>()

val tdTextMessageType = tdMessageToken<TdApi.MessageText>()

val tdUnsupportedMessageType = tdMessageToken<TdApi.MessageUnsupported>()

val tdVenueMessageType = tdMessageToken<TdApi.MessageVenue>()

val tdVideoMessageType = tdMessageToken<TdApi.MessageVideo>()

val tdVideoChatEndedMessageType = tdMessageToken<TdApi.MessageVideoChatEnded>()

val tdVideoChatScheduledMessageType = tdMessageToken<TdApi.MessageVideoChatScheduled>()

val tdVideoChatStartedMessageType = tdMessageToken<TdApi.MessageVideoChatStarted>()

val tdVideoNoteMessageType = tdMessageToken<TdApi.MessageVideoNote>()

val tdVoiceNoteMessageType = tdMessageToken<TdApi.MessageVoiceNote>()

val tdWebAppDataReceivedMessageType = tdMessageToken<TdApi.MessageWebAppDataReceived>()

val tdWebAppDataSentMessageType = tdMessageToken<TdApi.MessageWebAppDataSent>()

val tdWebsiteConnectedMessageType = tdMessageToken<TdApi.MessageWebsiteConnected>()

val tdUpdateAccessHashType = tdUpdateToken<TdApi.UpdateAccessHash>()

val tdUpdateActiveEmojiReactionsType = tdUpdateToken<TdApi.UpdateActiveEmojiReactions>()

val tdUpdateActiveNotificationsType = tdUpdateToken<TdApi.UpdateActiveNotifications>()

val tdUpdateAnimatedEmojiMessageClickedType = tdUpdateToken<TdApi.UpdateAnimatedEmojiMessageClicked>()

val tdUpdateAnimationSearchParametersType = tdUpdateToken<TdApi.UpdateAnimationSearchParameters>()

val tdUpdateAttachmentMenuBotsType = tdUpdateToken<TdApi.UpdateAttachmentMenuBots>()

val tdUpdateAuthorizationStateType = tdUpdateToken<TdApi.UpdateAuthorizationState>()

val tdUpdateBasicGroupType = tdUpdateToken<TdApi.UpdateBasicGroup>()

val tdUpdateBasicGroupFullInfoType = tdUpdateToken<TdApi.UpdateBasicGroupFullInfo>()

val tdUpdateCallType = tdUpdateToken<TdApi.UpdateCall>()

val tdUpdateChatActionType = tdUpdateToken<TdApi.UpdateChatAction>()

val tdUpdateChatActionBarType = tdUpdateToken<TdApi.UpdateChatActionBar>()

val tdUpdateChatAvailableReactionsType = tdUpdateToken<TdApi.UpdateChatAvailableReactions>()

val tdUpdateChatDefaultDisableNotificationType = tdUpdateToken<TdApi.UpdateChatDefaultDisableNotification>()

val tdUpdateChatDraftMessageType = tdUpdateToken<TdApi.UpdateChatDraftMessage>()

val tdUpdateChatFiltersType = tdUpdateToken<TdApi.UpdateChatFilters>()

val tdUpdateChatHasProtectedContentType = tdUpdateToken<TdApi.UpdateChatHasProtectedContent>()

val tdUpdateChatHasScheduledMessagesType = tdUpdateToken<TdApi.UpdateChatHasScheduledMessages>()

val tdUpdateChatIsBlockedType = tdUpdateToken<TdApi.UpdateChatIsBlocked>()

val tdUpdateChatIsMarkedAsUnreadType = tdUpdateToken<TdApi.UpdateChatIsMarkedAsUnread>()

val tdUpdateChatLastMessageType = tdUpdateToken<TdApi.UpdateChatLastMessage>()

val tdUpdateChatMemberType = tdUpdateToken<TdApi.UpdateChatMember>()

val tdUpdateChatMessageAutoDeleteTimeType = tdUpdateToken<TdApi.UpdateChatMessageAutoDeleteTime>()

val tdUpdateChatMessageSenderType = tdUpdateToken<TdApi.UpdateChatMessageSender>()

val tdUpdateChatNotificationSettingsType = tdUpdateToken<TdApi.UpdateChatNotificationSettings>()

val tdUpdateChatOnlineMemberCountType = tdUpdateToken<TdApi.UpdateChatOnlineMemberCount>()

val tdUpdateChatPendingJoinRequestsType = tdUpdateToken<TdApi.UpdateChatPendingJoinRequests>()

val tdUpdateChatPermissionsType = tdUpdateToken<TdApi.UpdateChatPermissions>()

val tdUpdateChatPhotoType = tdUpdateToken<TdApi.UpdateChatPhoto>()

val tdUpdateChatPositionType = tdUpdateToken<TdApi.UpdateChatPosition>()

val tdUpdateChatReadInboxType = tdUpdateToken<TdApi.UpdateChatReadInbox>()

val tdUpdateChatReadOutboxType = tdUpdateToken<TdApi.UpdateChatReadOutbox>()

val tdUpdateChatReplyMarkupType = tdUpdateToken<TdApi.UpdateChatReplyMarkup>()

val tdUpdateChatThemeType = tdUpdateToken<TdApi.UpdateChatTheme>()

val tdUpdateChatThemesType = tdUpdateToken<TdApi.UpdateChatThemes>()

val tdUpdateChatTitleType = tdUpdateToken<TdApi.UpdateChatTitle>()

val tdUpdateChatUnreadMentionCountType = tdUpdateToken<TdApi.UpdateChatUnreadMentionCount>()

val tdUpdateChatUnreadReactionCountType = tdUpdateToken<TdApi.UpdateChatUnreadReactionCount>()

val tdUpdateChatVideoChatType = tdUpdateToken<TdApi.UpdateChatVideoChat>()

val tdUpdateConnectionStateType = tdUpdateToken<TdApi.UpdateConnectionState>()

val tdUpdateDefaultReactionTypeType = tdUpdateToken<TdApi.UpdateDefaultReactionType>()

val tdUpdateDeleteMessagesType = tdUpdateToken<TdApi.UpdateDeleteMessages>()

val tdUpdateDiceEmojisType = tdUpdateToken<TdApi.UpdateDiceEmojis>()

val tdUpdateFavoriteStickersType = tdUpdateToken<TdApi.UpdateFavoriteStickers>()

val tdUpdateFileType = tdUpdateToken<TdApi.UpdateFile>()

val tdUpdateFileAddedToDownloadsType = tdUpdateToken<TdApi.UpdateFileAddedToDownloads>()

val tdUpdateFileDownloadType = tdUpdateToken<TdApi.UpdateFileDownload>()

val tdUpdateFileDownloadsType = tdUpdateToken<TdApi.UpdateFileDownloads>()

val tdUpdateFileGenerationStartType = tdUpdateToken<TdApi.UpdateFileGenerationStart>()

val tdUpdateFileGenerationStopType = tdUpdateToken<TdApi.UpdateFileGenerationStop>()

val tdUpdateFileRemovedFromDownloadsType = tdUpdateToken<TdApi.UpdateFileRemovedFromDownloads>()

val tdUpdateForumTopicInfoType = tdUpdateToken<TdApi.UpdateForumTopicInfo>()

val tdUpdateGroupCallType = tdUpdateToken<TdApi.UpdateGroupCall>()

val tdUpdateGroupCallParticipantType = tdUpdateToken<TdApi.UpdateGroupCallParticipant>()

val tdUpdateHavePendingNotificationsType = tdUpdateToken<TdApi.UpdateHavePendingNotifications>()

val tdUpdateInstalledStickerSetsType = tdUpdateToken<TdApi.UpdateInstalledStickerSets>()

val tdUpdateLanguagePackStringsType = tdUpdateToken<TdApi.UpdateLanguagePackStrings>()

val tdUpdateMessageContentType = tdUpdateToken<TdApi.UpdateMessageContent>()

val tdUpdateMessageContentOpenedType = tdUpdateToken<TdApi.UpdateMessageContentOpened>()

val tdUpdateMessageEditedType = tdUpdateToken<TdApi.UpdateMessageEdited>()

val tdUpdateMessageInteractionInfoType = tdUpdateToken<TdApi.UpdateMessageInteractionInfo>()

val tdUpdateMessageIsPinnedType = tdUpdateToken<TdApi.UpdateMessageIsPinned>()

val tdUpdateMessageLiveLocationViewedType = tdUpdateToken<TdApi.UpdateMessageLiveLocationViewed>()

val tdUpdateMessageMentionReadType = tdUpdateToken<TdApi.UpdateMessageMentionRead>()

val tdUpdateMessageSendAcknowledgedType = tdUpdateToken<TdApi.UpdateMessageSendAcknowledged>()

val tdUpdateMessageSendFailedType = tdUpdateToken<TdApi.UpdateMessageSendFailed>()

val tdUpdateMessageSendSucceededType = tdUpdateToken<TdApi.UpdateMessageSendSucceeded>()

val tdUpdateMessageUnreadReactionsType = tdUpdateToken<TdApi.UpdateMessageUnreadReactions>()

val tdUpdateNewCallSignalingDataType = tdUpdateToken<TdApi.UpdateNewCallSignalingData>()

val tdUpdateNewCallbackQueryType = tdUpdateToken<TdApi.UpdateNewCallbackQuery>()

val tdUpdateNewChatType = tdUpdateToken<TdApi.UpdateNewChat>()

val tdUpdateNewChatJoinRequestType = tdUpdateToken<TdApi.UpdateNewChatJoinRequest>()

val tdUpdateNewChosenInlineResultType = tdUpdateToken<TdApi.UpdateNewChosenInlineResult>()

val tdUpdateNewCustomEventType = tdUpdateToken<TdApi.UpdateNewCustomEvent>()

val tdUpdateNewCustomQueryType = tdUpdateToken<TdApi.UpdateNewCustomQuery>()

val tdUpdateNewInlineCallbackQueryType = tdUpdateToken<TdApi.UpdateNewInlineCallbackQuery>()

val tdUpdateNewInlineQueryType = tdUpdateToken<TdApi.UpdateNewInlineQuery>()

val tdUpdateNewMessageType = tdUpdateToken<TdApi.UpdateNewMessage>()

val tdUpdateNewPreCheckoutQueryType = tdUpdateToken<TdApi.UpdateNewPreCheckoutQuery>()

val tdUpdateNewShippingQueryType = tdUpdateToken<TdApi.UpdateNewShippingQuery>()

val tdUpdateNotificationType = tdUpdateToken<TdApi.UpdateNotification>()

val tdUpdateNotificationGroupType = tdUpdateToken<TdApi.UpdateNotificationGroup>()

val tdUpdateOptionType = tdUpdateToken<TdApi.UpdateOption>()

val tdUpdatePollType = tdUpdateToken<TdApi.UpdatePoll>()

val tdUpdatePollAnswerType = tdUpdateToken<TdApi.UpdatePollAnswer>()

val tdUpdateRecentStickersType = tdUpdateToken<TdApi.UpdateRecentStickers>()

val tdUpdateSavedAnimationsType = tdUpdateToken<TdApi.UpdateSavedAnimations>()

val tdUpdateSavedNotificationSoundsType = tdUpdateToken<TdApi.UpdateSavedNotificationSounds>()

val tdUpdateScopeNotificationSettingsType = tdUpdateToken<TdApi.UpdateScopeNotificationSettings>()

val tdUpdateSecretChatType = tdUpdateToken<TdApi.UpdateSecretChat>()

val tdUpdateSelectedBackgroundType = tdUpdateToken<TdApi.UpdateSelectedBackground>()

val tdUpdateServiceNotificationType = tdUpdateToken<TdApi.UpdateServiceNotification>()

val tdUpdateStickerSetType = tdUpdateToken<TdApi.UpdateStickerSet>()

val tdUpdateSuggestedActionsType = tdUpdateToken<TdApi.UpdateSuggestedActions>()

val tdUpdateSupergroupType = tdUpdateToken<TdApi.UpdateSupergroup>()

val tdUpdateSupergroupFullInfoType = tdUpdateToken<TdApi.UpdateSupergroupFullInfo>()

val tdUpdateTermsOfServiceType = tdUpdateToken<TdApi.UpdateTermsOfService>()

val tdUpdateTrendingStickerSetsType = tdUpdateToken<TdApi.UpdateTrendingStickerSets>()

val tdUpdateUnreadChatCountType = tdUpdateToken<TdApi.UpdateUnreadChatCount>()

val tdUpdateUnreadMessageCountType = tdUpdateToken<TdApi.UpdateUnreadMessageCount>()

val tdUpdateUserType = tdUpdateToken<TdApi.UpdateUser>()

val tdUpdateUserFullInfoType = tdUpdateToken<TdApi.UpdateUserFullInfo>()

val tdUpdateUserPrivacySettingRulesType = tdUpdateToken<TdApi.UpdateUserPrivacySettingRules>()

val tdUpdateUserStatusType = tdUpdateToken<TdApi.UpdateUserStatus>()

val tdUpdateUsersNearbyType = tdUpdateToken<TdApi.UpdateUsersNearby>()

val tdUpdateWebAppMessageSentType = tdUpdateToken<TdApi.UpdateWebAppMessageSent>()
