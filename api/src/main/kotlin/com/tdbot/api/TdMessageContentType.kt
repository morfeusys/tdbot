package com.tdbot.api

import com.justai.jaicf.channel.td.OnlyIf
import com.justai.jaicf.channel.td.request.messageRequest
import com.justai.jaicf.channel.td.request.td
import it.tdlight.jni.TdApi
import kotlin.reflect.KClass

enum class TdMessageContentType(
    val contentClass: KClass<out TdApi.MessageContent>
) {
    AnimatedEmoji(TdApi.MessageAnimatedEmoji::class),
    Animation(TdApi.MessageAnimation::class),
    Audio(TdApi.MessageAudio::class),
    BasicGroupChatCreate(TdApi.MessageBasicGroupChatCreate::class),
    BotWriteAccessAllowed(TdApi.MessageBotWriteAccessAllowed::class),
    Call(TdApi.MessageCall::class),
    ChatAddMembers(TdApi.MessageChatAddMembers::class),
    ChatChangePhoto(TdApi.MessageChatChangePhoto::class),
    ChatChangeTitle(TdApi.MessageChatChangeTitle::class),
    ChatDeleteMember(TdApi.MessageChatDeleteMember::class),
    ChatDeletePhoto(TdApi.MessageChatDeletePhoto::class),
    ChatJoinByLink(TdApi.MessageChatJoinByLink::class),
    ChatJoinByRequest(TdApi.MessageChatJoinByRequest::class),
    ChatSetMessageAutoDeleteTime(TdApi.MessageChatSetMessageAutoDeleteTime::class),
    ChatSetTheme(TdApi.MessageChatSetTheme::class),
    ChatUpgradeFrom(TdApi.MessageChatUpgradeFrom::class),
    ChatUpgradeTo(TdApi.MessageChatUpgradeTo::class),
    Contact(TdApi.MessageContact::class),
    ContactRegistered(TdApi.MessageContactRegistered::class),
    CustomServiceAction(TdApi.MessageCustomServiceAction::class),
    Dice(TdApi.MessageDice::class),
    Document(TdApi.MessageDocument::class),
    ExpiredPhoto(TdApi.MessageExpiredPhoto::class),
    ExpiredVideo(TdApi.MessageExpiredVideo::class),
    ForumTopicCreated(TdApi.MessageForumTopicCreated::class),
    ForumTopicEdited(TdApi.MessageForumTopicEdited::class),
    ForumTopicIsClosedToggled(TdApi.MessageForumTopicIsClosedToggled::class),
    ForumTopicIsHiddenToggled(TdApi.MessageForumTopicIsHiddenToggled::class),
    Game(TdApi.MessageGame::class),
    GameScore(TdApi.MessageGameScore::class),
    GiftedPremium(TdApi.MessageGiftedPremium::class),
    InviteVideoChatParticipants(TdApi.MessageInviteVideoChatParticipants::class),
    Invoice(TdApi.MessageInvoice::class),
    Location(TdApi.MessageLocation::class),
    PassportDataReceived(TdApi.MessagePassportDataReceived::class),
    PassportDataSent(TdApi.MessagePassportDataSent::class),
    PaymentSuccessful(TdApi.MessagePaymentSuccessful::class),
    PaymentSuccessfulBot(TdApi.MessagePaymentSuccessfulBot::class),
    Photo(TdApi.MessagePhoto::class),
    PinMessage(TdApi.MessagePinMessage::class),
    Poll(TdApi.MessagePoll::class),
    ProximityAlertTriggered(TdApi.MessageProximityAlertTriggered::class),
    ScreenshotTaken(TdApi.MessageScreenshotTaken::class),
    Sticker(TdApi.MessageSticker::class),
    SuggestProfilePhoto(TdApi.MessageSuggestProfilePhoto::class),
    SupergroupChatCreate(TdApi.MessageSupergroupChatCreate::class),
    Text(TdApi.MessageText::class),
    Unsupported(TdApi.MessageUnsupported::class),
    Venue(TdApi.MessageVenue::class),
    Video(TdApi.MessageVideo::class),
    VideoChatEnded(TdApi.MessageVideoChatEnded::class),
    VideoChatScheduled(TdApi.MessageVideoChatScheduled::class),
    VideoChatStarted(TdApi.MessageVideoChatStarted::class),
    VideoNote(TdApi.MessageVideoNote::class),
    VoiceNote(TdApi.MessageVoiceNote::class),
    WebAppDataReceived(TdApi.MessageWebAppDataReceived::class),
    WebAppDataSent(TdApi.MessageWebAppDataSent::class),
    WebsiteConnected(TdApi.MessageWebsiteConnected::class),
}

fun TdMessageContentType.isInstance(content: TdApi.MessageContent) =
    contentClass.isInstance(content)

fun TdApi.MessageContent.isType(type: TdMessageContentType) =
    type.isInstance(this)

val TdMessageContentType.condition: OnlyIf
    get() = { request.td?.messageRequest?.content?.isType(this@condition) ?: false }