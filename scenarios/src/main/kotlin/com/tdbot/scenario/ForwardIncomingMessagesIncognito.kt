package com.tdbot.scenario

import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.MessageEntity
import com.github.kotlintelegrambot.entities.MessageEntity.Type.*
import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.client.TdTelegramApi
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onAnyNewMessage
import com.justai.jaicf.channel.td.scenario.onAnyNewTextMessage
import com.justai.jaicf.channel.td.scenario.onReady
import com.tdbot.api.TdBotApi
import com.tdbot.scenario.utils.searchChats
import it.tdlight.jni.TdApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.io.File

fun ForwardIncomingMessagesIncognito(
    toChat: String,
    tdBotApi: TdBotApi
) = TdScenario {
    val logger = LoggerFactory.getLogger("ForwardIncomingMessagesIncognito-$toChat")

    var toChatId: Long? = null
    val chatId by lazy { ChatId.fromId(toChatId!!) }

    lateinit var tdApi: TdTelegramApi

    val chats = mutableMapOf<Long, TdApi.Chat>()
    val users = mutableMapOf<Long, TdApi.User>()

    onReady {
        tdApi = api
        api.searchChats(toChat) { res ->
            if (res.isError || res.get().totalCount == 0) {
                logger.error("Cannot find chat \"$toChat\"")
            } else {
                toChatId = res.get().chatIds.first()
                logger.info("Found chat \"$toChat\" = $toChatId")
            }
        }
    }

    suspend fun getChat(chatId: Long) =
        chats.getOrPut(chatId) { tdApi.send(TdApi.GetChat(chatId)) }

    suspend fun getUser(userId: Long) =
        users.getOrPut(userId) { tdApi.send(TdApi.GetUser(userId)) }

    suspend fun TdTelegramApi.downloadFile(file: TdApi.File) =
        send(TdApi.DownloadFile(file.id, 1, 0L, 0L, true))

    suspend fun <R> withTempFile(file: TdApi.File, handler: (file: File) -> R) =
        File(tdApi.downloadFile(file).local.path).let {
            try {
                handler(it)
            } finally {
                it.delete()
            }
        }

    fun sendText(text: String, entities: List<MessageEntity>?) =
        tdBotApi.telegram.sendMessage(chatId, text, entities = entities)

    suspend fun sendPhoto(caption: String, entities: List<MessageEntity>?, content: TdApi.MessagePhoto) =
        withTempFile(content.photo.sizes.maxBy { it.photo.size }.photo) { file ->
            tdBotApi.telegram.sendPhoto(chatId, file, caption, captionEntities = entities)
        }

    suspend fun sendAnimation(caption: String, entities: List<MessageEntity>?, content: TdApi.MessageAnimation) =
        withTempFile(content.animation.animation) { file ->
            tdBotApi.telegram.sendAnimation(chatId, file, content.animation.duration, content.animation.width, content.animation.height, caption, captionEntities = entities)
        }

    suspend fun sendVideo(caption: String, entities: List<MessageEntity>?, content: TdApi.MessageVideo) =
        withTempFile(content.video.video) { file ->
            tdBotApi.telegram.sendVideo(chatId, file, content.video.duration, content.video.width, content.video.height, caption, captionEntities = entities)
        }

    suspend fun sendVoiceNote(caption: String, entities: List<MessageEntity>?, content: TdApi.MessageVoiceNote) =
        withTempFile(content.voiceNote.voice) { file ->
            tdBotApi.telegram.sendVoice(chatId, file, caption, null, entities, content.voiceNote.duration)
        }

    suspend fun sendAudio(caption: String, entities: List<MessageEntity>?, content: TdApi.MessageAudio) =
        withTempFile(content.audio.audio) { file ->
            tdBotApi.telegram.sendAudio(chatId, file, caption, null, entities, content.audio.duration, content.audio.performer, content.audio.title)
        }

    suspend fun sendVideoNote(content: TdApi.MessageVideoNote) =
        withTempFile(content.videoNote.video) { file ->
            tdBotApi.telegram.sendVideoNote(chatId, file, content.videoNote.duration, content.videoNote.length)
        }

    suspend fun sendMessage(from: TdApi.Chat, sender: TdApi.Object, forwardedFrom: TdApi.Object?, content: TdApi.MessageContent) {
        val senderTitle = when (sender) {
            is TdApi.Chat -> sender.title
            is TdApi.User -> "${sender.firstName} ${sender.lastName}".trim()
            else -> from.title
        }

        val fromTitle = when {
            senderTitle != from.title -> from.title
            else -> null
        }

        val forwardedFromTitle = when (forwardedFrom) {
            is TdApi.Chat -> forwardedFrom.title
            is TdApi.User -> "${forwardedFrom.firstName} ${forwardedFrom.lastName}".trim()
            else -> null
        }?.let {
            when {
                it != from.title -> it
                else -> null
            }
        }

        val title = senderTitle +
                fromTitle?.let { " → $it" }.orEmpty() +
                forwardedFromTitle?.let { " ← $it" }.orEmpty() + "\n" +
                content.text?.takeIf { it.text.length > 25 }?.let { "\n" }.orEmpty()

        val offset = title.length
        val text = title + content.text?.text.orEmpty()

        val entities = mutableListOf(MessageEntity(BOLD, 0, title.length))

        entities.addAll(
            content.text?.entities?.mapNotNull { e ->
                val type = e.type
                when (type) {
                    is TdApi.TextEntityTypeUrl -> MessageEntity(URL, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeTextUrl -> MessageEntity(TEXT_LINK, e.offset + offset, e.length, url = type.url)
                    is TdApi.TextEntityTypeBold -> MessageEntity(BOLD, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeBotCommand -> MessageEntity(BOT_COMMAND, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeCashtag -> MessageEntity(CASHTAG, e.offset + offset, e.length)
                    is TdApi.TextEntityTypePreCode -> MessageEntity(CODE, e.offset + offset, e.length, language = type.language)
                    is TdApi.TextEntityTypeCustomEmoji -> MessageEntity(CUSTOM_EMOJI, e.offset + offset, e.length, customEmojiId = type.customEmojiId)
                    is TdApi.TextEntityTypeItalic -> MessageEntity(ITALIC, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeEmailAddress -> MessageEntity(EMAIL, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeHashtag -> MessageEntity(HASHTAG, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeMention -> MessageEntity(MENTION, e.offset + offset, e.length)
                    is TdApi.TextEntityTypePhoneNumber -> MessageEntity(PHONE_NUMBER, e.offset + offset, e.length)
                    is TdApi.TextEntityTypePre -> MessageEntity(PRE, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeStrikethrough -> MessageEntity(STRIKETHROUGH, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeUnderline -> MessageEntity(UNDERLINE, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeSpoiler -> MessageEntity(SPOILER, e.offset + offset, e.length)
                    else -> null
                }
            }.orEmpty()
        )

        when (content) {
            is TdApi.MessageText -> sendText(text, entities)
            is TdApi.MessagePhoto -> sendPhoto(text, entities, content)
            is TdApi.MessageAnimation -> sendAnimation(text, entities, content)
            is TdApi.MessageVideo -> sendVideo(text, entities, content)
            is TdApi.MessageVoiceNote -> sendVoiceNote(text, entities, content)
            is TdApi.MessageAudio -> sendAudio(text, entities, content)
            is TdApi.MessageVideoNote -> sendVideoNote(content)
            else -> null
        }
    }

    fun sendMessage(request: TdNewMessageRequest<*>) {
        GlobalScope.launch {
            val chat = getChat(request.chatId!!)

            val sender = request.update.message.senderId.let { sender ->
                when (sender) {
                    is TdApi.MessageSenderChat -> if (sender.chatId != chat.id) { getChat(sender.chatId) } else chat
                    is TdApi.MessageSenderUser -> if (sender.userId != chat.id) { getUser(sender.userId) } else chat
                    else -> chat
                }
            }

            val forwardedFrom = request.update.message.forwardInfo?.origin?.let { origin ->
                when (origin) {
                    is TdApi.MessageForwardOriginChannel -> getChat(origin.chatId)
                    is TdApi.MessageForwardOriginChat -> getChat(origin.senderChatId)
                    is TdApi.MessageForwardOriginUser -> getUser(origin.senderUserId)
                    else -> null
                }
            }

            sendMessage(chat, sender, forwardedFrom, request.content)
        }
    }

    onAnyNewMessage(isIncoming, isNotChat { toChatId }) {
        sendMessage(request)
    }

    onAnyNewTextMessage(isIncoming, isNotChat { toChatId }) {
        sendMessage(request)
    }
}