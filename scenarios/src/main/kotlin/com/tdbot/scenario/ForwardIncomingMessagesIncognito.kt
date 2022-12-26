package com.tdbot.scenario

import com.github.kotlintelegrambot.entities.ChatId
import com.github.kotlintelegrambot.entities.MessageEntity
import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.client.TdTelegramApi
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onAnyNewMessage
import com.justai.jaicf.channel.td.scenario.onAnyNewTextMessage
import com.justai.jaicf.channel.td.scenario.onReady
import com.justai.jaicf.helpers.kotlin.ifTrue
import com.tdbot.api.TdBotApi
import com.tdbot.scenario.utils.searchChats
import it.tdlight.client.TelegramError
import it.tdlight.jni.TdApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

fun ForwardIncomingMessagesIncognito(
    toChat: String,
    tdBotApi: TdBotApi
) = TdScenario {
    val logger = LoggerFactory.getLogger("ForwardIncomingMessagesIncognito-$toChat")

    var toChatId: Long? = null
    val chatId by lazy { ChatId.fromId(toChatId!!) }

    lateinit var tdApi: TdTelegramApi

    val chats = mutableMapOf<Long, TdApi.Chat>()

    onReady {
        tdApi = api
        api.searchChats(toChat) { res ->
            if (res.isError || res.get().totalCount == 0) {
                logger.error("Cannot find chat \"$toChat\"")
            } else {
                toChatId = res.get().chatIds.first()
            }
        }
    }

    suspend fun <R : TdApi.Object> TdTelegramApi.send(function: TdApi.Function<R>) =
        suspendCoroutine<R> { continuation ->
            send(function) { res ->
                if (res.isError) {
                    continuation.resumeWithException(TelegramError(res.error))
                } else {
                    continuation.resume(res.get())
                }
            }
        }

    suspend fun TdTelegramApi.getChat(chatId: Long) =
        send(TdApi.GetChat(chatId))

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

    suspend fun sendMessage(from: TdApi.Chat, forwardedForm: TdApi.Chat?, content: TdApi.MessageContent) {
        val title = from.title +
                forwardedForm?.let { " from ${it.title}" }.orEmpty() + "\n" +
                (content !is TdApi.MessageText).ifTrue { "\n" }.orEmpty()

        val offset = title.length
        val text = title + content.text?.text.orEmpty()

        val entities = mutableListOf(MessageEntity(MessageEntity.Type.BOLD, 0, from.title.length))
        forwardedForm?.let { f ->
            entities.add(MessageEntity(MessageEntity.Type.BOLD, title.indexOf("from") + 5, f.title.length))
        }

        entities.addAll(
            content.text?.entities?.mapNotNull { e ->
                val type = e.type
                when (type) {
                    is TdApi.TextEntityTypeUrl -> MessageEntity(MessageEntity.Type.URL, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeTextUrl -> MessageEntity(MessageEntity.Type.TEXT_LINK, e.offset + offset, e.length, url = type.url)
                    is TdApi.TextEntityTypeBold -> MessageEntity(MessageEntity.Type.BOLD, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeBotCommand -> MessageEntity(MessageEntity.Type.BOT_COMMAND, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeCashtag -> MessageEntity(MessageEntity.Type.CASHTAG, e.offset + offset, e.length)
                    is TdApi.TextEntityTypePreCode -> MessageEntity(MessageEntity.Type.CODE, e.offset + offset, e.length, language = type.language)
                    is TdApi.TextEntityTypeCustomEmoji -> MessageEntity(MessageEntity.Type.CUSTOM_EMOJI, e.offset + offset, e.length, customEmojiId = type.customEmojiId)
                    is TdApi.TextEntityTypeItalic -> MessageEntity(MessageEntity.Type.ITALIC, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeEmailAddress -> MessageEntity(MessageEntity.Type.EMAIL, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeHashtag -> MessageEntity(MessageEntity.Type.HASHTAG, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeMention -> MessageEntity(MessageEntity.Type.MENTION, e.offset + offset, e.length)
                    is TdApi.TextEntityTypePhoneNumber -> MessageEntity(MessageEntity.Type.PHONE_NUMBER, e.offset + offset, e.length)
                    is TdApi.TextEntityTypePre -> MessageEntity(MessageEntity.Type.PRE, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeStrikethrough -> MessageEntity(MessageEntity.Type.STRIKETHROUGH, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeUnderline -> MessageEntity(MessageEntity.Type.UNDERLINE, e.offset + offset, e.length)
                    is TdApi.TextEntityTypeSpoiler -> MessageEntity(MessageEntity.Type.SPOILER, e.offset + offset, e.length)
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
            val chat = chats.getOrPut(request.chatId!!) {
                tdApi.getChat(request.chatId!!)
            }
            val forwardedFrom = request.update.message.forwardInfo?.origin?.let { origin ->
                when (origin) {
                    is TdApi.MessageForwardOriginChannel -> origin.chatId
                    else -> null
                }?.let { chatId ->
                    chats.getOrPut(chatId) {
                        tdApi.getChat(chatId)
                    }
                }
            }
            sendMessage(chat, forwardedFrom, request.content)
        }
    }

    onAnyNewMessage(isIncoming, isNotChat { toChatId }) {
        sendMessage(request)
    }

    onAnyNewTextMessage(isIncoming, isNotChat { toChatId }) {
        sendMessage(request)
    }
}