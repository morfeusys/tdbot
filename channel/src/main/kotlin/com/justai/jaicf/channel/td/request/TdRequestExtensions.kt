package com.justai.jaicf.channel.td.request

import it.tdlight.jni.TdApi

val DefaultTdRequest.animatedEmojiMessageRequest get() = this as? TdMessageRequest<TdApi.MessageAnimatedEmoji>

val DefaultTdRequest.animationMessageRequest get() = this as? TdMessageRequest<TdApi.MessageAnimation>

val DefaultTdRequest.audioMessageRequest get() = this as? TdMessageRequest<TdApi.MessageAudio>

val DefaultTdRequest.basicGroupChatCreateMessageRequest get() = this as? TdMessageRequest<TdApi.MessageBasicGroupChatCreate>

val DefaultTdRequest.botWriteAccessAllowedMessageRequest get() = this as? TdMessageRequest<TdApi.MessageBotWriteAccessAllowed>

val DefaultTdRequest.callMessageRequest get() = this as? TdMessageRequest<TdApi.MessageCall>

val DefaultTdRequest.chatAddMembersMessageRequest get() = this as? TdMessageRequest<TdApi.MessageChatAddMembers>

val DefaultTdRequest.chatChangePhotoMessageRequest get() = this as? TdMessageRequest<TdApi.MessageChatChangePhoto>

val DefaultTdRequest.chatChangeTitleMessageRequest get() = this as? TdMessageRequest<TdApi.MessageChatChangeTitle>

val DefaultTdRequest.chatDeleteMemberMessageRequest get() = this as? TdMessageRequest<TdApi.MessageChatDeleteMember>

val DefaultTdRequest.chatDeletePhotoMessageRequest get() = this as? TdMessageRequest<TdApi.MessageChatDeletePhoto>

val DefaultTdRequest.chatJoinByLinkMessageRequest get() = this as? TdMessageRequest<TdApi.MessageChatJoinByLink>

val DefaultTdRequest.chatJoinByRequestMessageRequest get() = this as? TdMessageRequest<TdApi.MessageChatJoinByRequest>

val DefaultTdRequest.chatSetMessageAutoDeleteTimeMessageRequest get() = this as? TdMessageRequest<TdApi.MessageChatSetMessageAutoDeleteTime>

val DefaultTdRequest.chatSetThemeMessageRequest get() = this as? TdMessageRequest<TdApi.MessageChatSetTheme>

val DefaultTdRequest.chatUpgradeFromMessageRequest get() = this as? TdMessageRequest<TdApi.MessageChatUpgradeFrom>

val DefaultTdRequest.chatUpgradeToMessageRequest get() = this as? TdMessageRequest<TdApi.MessageChatUpgradeTo>

val DefaultTdRequest.contactMessageRequest get() = this as? TdMessageRequest<TdApi.MessageContact>

val DefaultTdRequest.contactRegisteredMessageRequest get() = this as? TdMessageRequest<TdApi.MessageContactRegistered>

val DefaultTdRequest.customServiceActionMessageRequest get() = this as? TdMessageRequest<TdApi.MessageCustomServiceAction>

val DefaultTdRequest.diceMessageRequest get() = this as? TdMessageRequest<TdApi.MessageDice>

val DefaultTdRequest.documentMessageRequest get() = this as? TdMessageRequest<TdApi.MessageDocument>

val DefaultTdRequest.expiredPhotoMessageRequest get() = this as? TdMessageRequest<TdApi.MessageExpiredPhoto>

val DefaultTdRequest.expiredVideoMessageRequest get() = this as? TdMessageRequest<TdApi.MessageExpiredVideo>

val DefaultTdRequest.forumTopicCreatedMessageRequest get() = this as? TdMessageRequest<TdApi.MessageForumTopicCreated>

val DefaultTdRequest.forumTopicEditedMessageRequest get() = this as? TdMessageRequest<TdApi.MessageForumTopicEdited>

val DefaultTdRequest.forumTopicIsClosedToggledMessageRequest get() = this as? TdMessageRequest<TdApi.MessageForumTopicIsClosedToggled>

val DefaultTdRequest.forumTopicIsHiddenToggledMessageRequest get() = this as? TdMessageRequest<TdApi.MessageForumTopicIsHiddenToggled>

val DefaultTdRequest.gameMessageRequest get() = this as? TdMessageRequest<TdApi.MessageGame>

val DefaultTdRequest.gameScoreMessageRequest get() = this as? TdMessageRequest<TdApi.MessageGameScore>

val DefaultTdRequest.giftedPremiumMessageRequest get() = this as? TdMessageRequest<TdApi.MessageGiftedPremium>

val DefaultTdRequest.inviteVideoChatParticipantsMessageRequest get() = this as? TdMessageRequest<TdApi.MessageInviteVideoChatParticipants>

val DefaultTdRequest.invoiceMessageRequest get() = this as? TdMessageRequest<TdApi.MessageInvoice>

val DefaultTdRequest.locationMessageRequest get() = this as? TdMessageRequest<TdApi.MessageLocation>

val DefaultTdRequest.passportDataReceivedMessageRequest get() = this as? TdMessageRequest<TdApi.MessagePassportDataReceived>

val DefaultTdRequest.passportDataSentMessageRequest get() = this as? TdMessageRequest<TdApi.MessagePassportDataSent>

val DefaultTdRequest.paymentSuccessfulMessageRequest get() = this as? TdMessageRequest<TdApi.MessagePaymentSuccessful>

val DefaultTdRequest.paymentSuccessfulBotMessageRequest get() = this as? TdMessageRequest<TdApi.MessagePaymentSuccessfulBot>

val DefaultTdRequest.photoMessageRequest get() = this as? TdMessageRequest<TdApi.MessagePhoto>

val DefaultTdRequest.pinMessageMessageRequest get() = this as? TdMessageRequest<TdApi.MessagePinMessage>

val DefaultTdRequest.pollMessageRequest get() = this as? TdMessageRequest<TdApi.MessagePoll>

val DefaultTdRequest.proximityAlertTriggeredMessageRequest get() = this as? TdMessageRequest<TdApi.MessageProximityAlertTriggered>

val DefaultTdRequest.screenshotTakenMessageRequest get() = this as? TdMessageRequest<TdApi.MessageScreenshotTaken>

val DefaultTdRequest.stickerMessageRequest get() = this as? TdMessageRequest<TdApi.MessageSticker>

val DefaultTdRequest.suggestProfilePhotoMessageRequest get() = this as? TdMessageRequest<TdApi.MessageSuggestProfilePhoto>

val DefaultTdRequest.supergroupChatCreateMessageRequest get() = this as? TdMessageRequest<TdApi.MessageSupergroupChatCreate>

val DefaultTdRequest.textMessageRequest get() = this as? TdMessageRequest<TdApi.MessageText>

val DefaultTdRequest.unsupportedMessageRequest get() = this as? TdMessageRequest<TdApi.MessageUnsupported>

val DefaultTdRequest.venueMessageRequest get() = this as? TdMessageRequest<TdApi.MessageVenue>

val DefaultTdRequest.videoMessageRequest get() = this as? TdMessageRequest<TdApi.MessageVideo>

val DefaultTdRequest.videoChatEndedMessageRequest get() = this as? TdMessageRequest<TdApi.MessageVideoChatEnded>

val DefaultTdRequest.videoChatScheduledMessageRequest get() = this as? TdMessageRequest<TdApi.MessageVideoChatScheduled>

val DefaultTdRequest.videoChatStartedMessageRequest get() = this as? TdMessageRequest<TdApi.MessageVideoChatStarted>

val DefaultTdRequest.videoNoteMessageRequest get() = this as? TdMessageRequest<TdApi.MessageVideoNote>

val DefaultTdRequest.voiceNoteMessageRequest get() = this as? TdMessageRequest<TdApi.MessageVoiceNote>

val DefaultTdRequest.webAppDataReceivedMessageRequest get() = this as? TdMessageRequest<TdApi.MessageWebAppDataReceived>

val DefaultTdRequest.webAppDataSentMessageRequest get() = this as? TdMessageRequest<TdApi.MessageWebAppDataSent>

val DefaultTdRequest.websiteConnectedMessageRequest get() = this as? TdMessageRequest<TdApi.MessageWebsiteConnected>

val DefaultTdRequest.updateAccessHashRequest get() = this as? TdRequest<TdApi.UpdateAccessHash>

val DefaultTdRequest.updateActiveEmojiReactionsRequest get() = this as? TdRequest<TdApi.UpdateActiveEmojiReactions>

val DefaultTdRequest.updateActiveNotificationsRequest get() = this as? TdRequest<TdApi.UpdateActiveNotifications>

val DefaultTdRequest.updateAnimatedEmojiMessageClickedRequest get() = this as? TdRequest<TdApi.UpdateAnimatedEmojiMessageClicked>

val DefaultTdRequest.updateAnimationSearchParametersRequest get() = this as? TdRequest<TdApi.UpdateAnimationSearchParameters>

val DefaultTdRequest.updateAttachmentMenuBotsRequest get() = this as? TdRequest<TdApi.UpdateAttachmentMenuBots>

val DefaultTdRequest.updateAuthorizationStateRequest get() = this as? TdRequest<TdApi.UpdateAuthorizationState>

val DefaultTdRequest.updateBasicGroupRequest get() = this as? TdRequest<TdApi.UpdateBasicGroup>

val DefaultTdRequest.updateBasicGroupFullInfoRequest get() = this as? TdRequest<TdApi.UpdateBasicGroupFullInfo>

val DefaultTdRequest.updateCallRequest get() = this as? TdRequest<TdApi.UpdateCall>

val DefaultTdRequest.updateChatActionRequest get() = this as? TdRequest<TdApi.UpdateChatAction>

val DefaultTdRequest.updateChatActionBarRequest get() = this as? TdRequest<TdApi.UpdateChatActionBar>

val DefaultTdRequest.updateChatAvailableReactionsRequest get() = this as? TdRequest<TdApi.UpdateChatAvailableReactions>

val DefaultTdRequest.updateChatDefaultDisableNotificationRequest get() = this as? TdRequest<TdApi.UpdateChatDefaultDisableNotification>

val DefaultTdRequest.updateChatDraftMessageRequest get() = this as? TdRequest<TdApi.UpdateChatDraftMessage>

val DefaultTdRequest.updateChatFiltersRequest get() = this as? TdRequest<TdApi.UpdateChatFilters>

val DefaultTdRequest.updateChatHasProtectedContentRequest get() = this as? TdRequest<TdApi.UpdateChatHasProtectedContent>

val DefaultTdRequest.updateChatHasScheduledMessagesRequest get() = this as? TdRequest<TdApi.UpdateChatHasScheduledMessages>

val DefaultTdRequest.updateChatIsBlockedRequest get() = this as? TdRequest<TdApi.UpdateChatIsBlocked>

val DefaultTdRequest.updateChatIsMarkedAsUnreadRequest get() = this as? TdRequest<TdApi.UpdateChatIsMarkedAsUnread>

val DefaultTdRequest.updateChatLastMessageRequest get() = this as? TdRequest<TdApi.UpdateChatLastMessage>

val DefaultTdRequest.updateChatMemberRequest get() = this as? TdRequest<TdApi.UpdateChatMember>

val DefaultTdRequest.updateChatMessageAutoDeleteTimeRequest get() = this as? TdRequest<TdApi.UpdateChatMessageAutoDeleteTime>

val DefaultTdRequest.updateChatMessageSenderRequest get() = this as? TdRequest<TdApi.UpdateChatMessageSender>

val DefaultTdRequest.updateChatNotificationSettingsRequest get() = this as? TdRequest<TdApi.UpdateChatNotificationSettings>

val DefaultTdRequest.updateChatOnlineMemberCountRequest get() = this as? TdRequest<TdApi.UpdateChatOnlineMemberCount>

val DefaultTdRequest.updateChatPendingJoinRequestsRequest get() = this as? TdRequest<TdApi.UpdateChatPendingJoinRequests>

val DefaultTdRequest.updateChatPermissionsRequest get() = this as? TdRequest<TdApi.UpdateChatPermissions>

val DefaultTdRequest.updateChatPhotoRequest get() = this as? TdRequest<TdApi.UpdateChatPhoto>

val DefaultTdRequest.updateChatPositionRequest get() = this as? TdRequest<TdApi.UpdateChatPosition>

val DefaultTdRequest.updateChatReadInboxRequest get() = this as? TdRequest<TdApi.UpdateChatReadInbox>

val DefaultTdRequest.updateChatReadOutboxRequest get() = this as? TdRequest<TdApi.UpdateChatReadOutbox>

val DefaultTdRequest.updateChatReplyMarkupRequest get() = this as? TdRequest<TdApi.UpdateChatReplyMarkup>

val DefaultTdRequest.updateChatThemeRequest get() = this as? TdRequest<TdApi.UpdateChatTheme>

val DefaultTdRequest.updateChatThemesRequest get() = this as? TdRequest<TdApi.UpdateChatThemes>

val DefaultTdRequest.updateChatTitleRequest get() = this as? TdRequest<TdApi.UpdateChatTitle>

val DefaultTdRequest.updateChatUnreadMentionCountRequest get() = this as? TdRequest<TdApi.UpdateChatUnreadMentionCount>

val DefaultTdRequest.updateChatUnreadReactionCountRequest get() = this as? TdRequest<TdApi.UpdateChatUnreadReactionCount>

val DefaultTdRequest.updateChatVideoChatRequest get() = this as? TdRequest<TdApi.UpdateChatVideoChat>

val DefaultTdRequest.updateConnectionStateRequest get() = this as? TdRequest<TdApi.UpdateConnectionState>

val DefaultTdRequest.updateDefaultReactionTypeRequest get() = this as? TdRequest<TdApi.UpdateDefaultReactionType>

val DefaultTdRequest.updateDeleteMessagesRequest get() = this as? TdRequest<TdApi.UpdateDeleteMessages>

val DefaultTdRequest.updateDiceEmojisRequest get() = this as? TdRequest<TdApi.UpdateDiceEmojis>

val DefaultTdRequest.updateFavoriteStickersRequest get() = this as? TdRequest<TdApi.UpdateFavoriteStickers>

val DefaultTdRequest.updateFileRequest get() = this as? TdRequest<TdApi.UpdateFile>

val DefaultTdRequest.updateFileAddedToDownloadsRequest get() = this as? TdRequest<TdApi.UpdateFileAddedToDownloads>

val DefaultTdRequest.updateFileDownloadRequest get() = this as? TdRequest<TdApi.UpdateFileDownload>

val DefaultTdRequest.updateFileDownloadsRequest get() = this as? TdRequest<TdApi.UpdateFileDownloads>

val DefaultTdRequest.updateFileGenerationStartRequest get() = this as? TdRequest<TdApi.UpdateFileGenerationStart>

val DefaultTdRequest.updateFileGenerationStopRequest get() = this as? TdRequest<TdApi.UpdateFileGenerationStop>

val DefaultTdRequest.updateFileRemovedFromDownloadsRequest get() = this as? TdRequest<TdApi.UpdateFileRemovedFromDownloads>

val DefaultTdRequest.updateForumTopicInfoRequest get() = this as? TdRequest<TdApi.UpdateForumTopicInfo>

val DefaultTdRequest.updateGroupCallRequest get() = this as? TdRequest<TdApi.UpdateGroupCall>

val DefaultTdRequest.updateGroupCallParticipantRequest get() = this as? TdRequest<TdApi.UpdateGroupCallParticipant>

val DefaultTdRequest.updateHavePendingNotificationsRequest get() = this as? TdRequest<TdApi.UpdateHavePendingNotifications>

val DefaultTdRequest.updateInstalledStickerSetsRequest get() = this as? TdRequest<TdApi.UpdateInstalledStickerSets>

val DefaultTdRequest.updateLanguagePackStringsRequest get() = this as? TdRequest<TdApi.UpdateLanguagePackStrings>

val DefaultTdRequest.updateMessageContentRequest get() = this as? TdRequest<TdApi.UpdateMessageContent>

val DefaultTdRequest.updateMessageContentOpenedRequest get() = this as? TdRequest<TdApi.UpdateMessageContentOpened>

val DefaultTdRequest.updateMessageEditedRequest get() = this as? TdRequest<TdApi.UpdateMessageEdited>

val DefaultTdRequest.updateMessageInteractionInfoRequest get() = this as? TdRequest<TdApi.UpdateMessageInteractionInfo>

val DefaultTdRequest.updateMessageIsPinnedRequest get() = this as? TdRequest<TdApi.UpdateMessageIsPinned>

val DefaultTdRequest.updateMessageLiveLocationViewedRequest get() = this as? TdRequest<TdApi.UpdateMessageLiveLocationViewed>

val DefaultTdRequest.updateMessageMentionReadRequest get() = this as? TdRequest<TdApi.UpdateMessageMentionRead>

val DefaultTdRequest.updateMessageSendAcknowledgedRequest get() = this as? TdRequest<TdApi.UpdateMessageSendAcknowledged>

val DefaultTdRequest.updateMessageSendFailedRequest get() = this as? TdRequest<TdApi.UpdateMessageSendFailed>

val DefaultTdRequest.updateMessageSendSucceededRequest get() = this as? TdRequest<TdApi.UpdateMessageSendSucceeded>

val DefaultTdRequest.updateMessageUnreadReactionsRequest get() = this as? TdRequest<TdApi.UpdateMessageUnreadReactions>

val DefaultTdRequest.updateNewCallSignalingDataRequest get() = this as? TdRequest<TdApi.UpdateNewCallSignalingData>

val DefaultTdRequest.updateNewCallbackQueryRequest get() = this as? TdRequest<TdApi.UpdateNewCallbackQuery>

val DefaultTdRequest.updateNewChatRequest get() = this as? TdRequest<TdApi.UpdateNewChat>

val DefaultTdRequest.updateNewChatJoinRequestRequest get() = this as? TdRequest<TdApi.UpdateNewChatJoinRequest>

val DefaultTdRequest.updateNewChosenInlineResultRequest get() = this as? TdRequest<TdApi.UpdateNewChosenInlineResult>

val DefaultTdRequest.updateNewCustomEventRequest get() = this as? TdRequest<TdApi.UpdateNewCustomEvent>

val DefaultTdRequest.updateNewCustomQueryRequest get() = this as? TdRequest<TdApi.UpdateNewCustomQuery>

val DefaultTdRequest.updateNewInlineCallbackQueryRequest get() = this as? TdRequest<TdApi.UpdateNewInlineCallbackQuery>

val DefaultTdRequest.updateNewInlineQueryRequest get() = this as? TdRequest<TdApi.UpdateNewInlineQuery>

val DefaultTdRequest.updateNewMessageRequest get() = this as? TdRequest<TdApi.UpdateNewMessage>

val DefaultTdRequest.updateNewPreCheckoutQueryRequest get() = this as? TdRequest<TdApi.UpdateNewPreCheckoutQuery>

val DefaultTdRequest.updateNewShippingQueryRequest get() = this as? TdRequest<TdApi.UpdateNewShippingQuery>

val DefaultTdRequest.updateNotificationRequest get() = this as? TdRequest<TdApi.UpdateNotification>

val DefaultTdRequest.updateNotificationGroupRequest get() = this as? TdRequest<TdApi.UpdateNotificationGroup>

val DefaultTdRequest.updateOptionRequest get() = this as? TdRequest<TdApi.UpdateOption>

val DefaultTdRequest.updatePollRequest get() = this as? TdRequest<TdApi.UpdatePoll>

val DefaultTdRequest.updatePollAnswerRequest get() = this as? TdRequest<TdApi.UpdatePollAnswer>

val DefaultTdRequest.updateRecentStickersRequest get() = this as? TdRequest<TdApi.UpdateRecentStickers>

val DefaultTdRequest.updateSavedAnimationsRequest get() = this as? TdRequest<TdApi.UpdateSavedAnimations>

val DefaultTdRequest.updateSavedNotificationSoundsRequest get() = this as? TdRequest<TdApi.UpdateSavedNotificationSounds>

val DefaultTdRequest.updateScopeNotificationSettingsRequest get() = this as? TdRequest<TdApi.UpdateScopeNotificationSettings>

val DefaultTdRequest.updateSecretChatRequest get() = this as? TdRequest<TdApi.UpdateSecretChat>

val DefaultTdRequest.updateSelectedBackgroundRequest get() = this as? TdRequest<TdApi.UpdateSelectedBackground>

val DefaultTdRequest.updateServiceNotificationRequest get() = this as? TdRequest<TdApi.UpdateServiceNotification>

val DefaultTdRequest.updateStickerSetRequest get() = this as? TdRequest<TdApi.UpdateStickerSet>

val DefaultTdRequest.updateSuggestedActionsRequest get() = this as? TdRequest<TdApi.UpdateSuggestedActions>

val DefaultTdRequest.updateSupergroupRequest get() = this as? TdRequest<TdApi.UpdateSupergroup>

val DefaultTdRequest.updateSupergroupFullInfoRequest get() = this as? TdRequest<TdApi.UpdateSupergroupFullInfo>

val DefaultTdRequest.updateTermsOfServiceRequest get() = this as? TdRequest<TdApi.UpdateTermsOfService>

val DefaultTdRequest.updateTrendingStickerSetsRequest get() = this as? TdRequest<TdApi.UpdateTrendingStickerSets>

val DefaultTdRequest.updateUnreadChatCountRequest get() = this as? TdRequest<TdApi.UpdateUnreadChatCount>

val DefaultTdRequest.updateUnreadMessageCountRequest get() = this as? TdRequest<TdApi.UpdateUnreadMessageCount>

val DefaultTdRequest.updateUserRequest get() = this as? TdRequest<TdApi.UpdateUser>

val DefaultTdRequest.updateUserFullInfoRequest get() = this as? TdRequest<TdApi.UpdateUserFullInfo>

val DefaultTdRequest.updateUserPrivacySettingRulesRequest get() = this as? TdRequest<TdApi.UpdateUserPrivacySettingRules>

val DefaultTdRequest.updateUserStatusRequest get() = this as? TdRequest<TdApi.UpdateUserStatus>

val DefaultTdRequest.updateUsersNearbyRequest get() = this as? TdRequest<TdApi.UpdateUsersNearby>

val DefaultTdRequest.updateWebAppMessageSentRequest get() = this as? TdRequest<TdApi.UpdateWebAppMessageSent>
