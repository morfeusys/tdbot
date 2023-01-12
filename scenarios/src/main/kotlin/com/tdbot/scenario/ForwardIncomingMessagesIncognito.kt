package com.tdbot.scenario

import com.justai.jaicf.channel.td.*
import com.justai.jaicf.channel.td.api.TdTelegramApi
import com.justai.jaicf.channel.td.api.searchChats
import com.justai.jaicf.channel.td.api.sendMessage
import com.justai.jaicf.channel.td.request.TdMessageRequest
import com.justai.jaicf.channel.td.request.chatId
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onAnyMessage
import com.justai.jaicf.channel.td.scenario.onReady
import com.tdbot.api.TdBotApi
import it.tdlight.jni.TdApi
import org.slf4j.LoggerFactory
import java.io.File

fun ForwardIncomingMessagesIncognito(
    toChat: String,
    tdBotApi: TdBotApi
) = TdScenario {
    val logger = LoggerFactory.getLogger("ForwardIncomingMessagesIncognito")

    var toChatId: Long? = null

    lateinit var tdApi: TdTelegramApi

    val chats = mutableMapOf<Long, TdApi.Chat>()
    val users = mutableMapOf<Long, TdApi.User>()

    onReady {
        tdApi = api
        toChatId = api.searchChats(toChat).chatIds.firstOrNull()
        if (toChatId == null) {
            logger.error("Cannot find chat \"$toChat\"")
        }
    }

    fun getChat(chatId: Long) =
        chats.getOrPut(chatId) { tdApi.send(TdApi.GetChat(chatId)) }

    fun getUser(userId: Long) =
        users.getOrPut(userId) { tdApi.send(TdApi.GetUser(userId)) }

    fun TdTelegramApi.downloadFile(file: TdApi.File) =
        send(TdApi.DownloadFile(file.id, 1, 0L, 0L, true))

    fun <R> withTempFile(file: TdApi.File, handler: (file: TdApi.InputFileLocal) -> R) =
        File(tdApi.downloadFile(file).local.path).let { local ->
            try {
                val res = handler(TdApi.InputFileLocal(local.absolutePath))
                if (res is TdApi.Message) {
                    tdBotApi.telegram.awaitMessage(res.id)
                }
            } finally {
                local.delete()
            }
        }

    fun sendText(text: TdApi.FormattedText) =
        tdBotApi.telegram.sendMessage(toChatId!!, content = TdMessage.text(text))

    fun sendPhoto(caption: TdApi.FormattedText, content: TdApi.MessagePhoto) =
        withTempFile(content.photo.sizes.maxBy { it.photo.size }.photo) { file ->
            tdBotApi.telegram.sendMessage(toChatId!!, content = TdMessage.photo(file, caption = caption))
        }

    fun sendAnimation(caption: TdApi.FormattedText, content: TdApi.MessageAnimation) =
        withTempFile(content.animation.animation) { file ->
            tdBotApi.telegram.sendMessage(
                toChatId!!,
                content = TdMessage.animation(
                    file,
                    null,
                    null,
                    content.animation.duration,
                    content.animation.width,
                    content.animation.height,
                    caption
                )
            )
        }

    fun sendVideo(caption: TdApi.FormattedText, content: TdApi.MessageVideo) =
        withTempFile(content.video.video) { file ->
            tdBotApi.telegram.sendMessage(
                toChatId!!,
                content = TdMessage.video(
                    file,
                    null,
                    null,
                    content.video.duration,
                    content.video.width,
                    content.video.height,
                    content.video.supportsStreaming,
                    caption
                )
            )
        }

    fun sendVoiceNote(caption: TdApi.FormattedText, content: TdApi.MessageVoiceNote) =
        withTempFile(content.voiceNote.voice) { file ->
            tdBotApi.telegram.sendMessage(
                toChatId!!,
                content = TdMessage.voiceNote(file, content.voiceNote.duration, content.voiceNote.waveform, caption)
            )
        }

    fun sendAudio(caption: TdApi.FormattedText, content: TdApi.MessageAudio) =
        withTempFile(content.audio.audio) { file ->
            tdBotApi.telegram.sendMessage(
                toChatId!!,
                content = TdMessage.audio(
                    file,
                    null,
                    content.audio.duration,
                    content.audio.title,
                    content.audio.performer,
                    caption
                )
            )
        }

    fun sendVideoNote(content: TdApi.MessageVideoNote) =
        withTempFile(content.videoNote.video) { file ->
            tdBotApi.telegram.sendMessage(
                toChatId!!,
                content = TdMessage.videoNote(file, null, content.videoNote.duration, content.videoNote.length)
            )
        }

    fun sendMessage(
        from: TdApi.Chat,
        sender: TdApi.Object,
        forwardedFrom: TdApi.Object?,
        content: TdApi.MessageContent
    ) {
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
                content.takeIf { it !is TdApi.MessageText }?.let { "\n" }.orEmpty()

        val offset = title.length
        val text = title + content.text?.text.orEmpty()

        val entities = mutableListOf(TdApi.TextEntity(0, title.length, TdApi.TextEntityTypeBold()))

        content.text?.entities?.mapTo(entities) { e ->
            TdApi.TextEntity(e.offset + offset, e.length, e.type)
        }

        val caption = TdApi.FormattedText(text, entities.toTypedArray())
        when (content) {
            is TdApi.MessageText -> sendText(caption)
            is TdApi.MessagePhoto -> sendPhoto(caption, content)
            is TdApi.MessageAnimation -> sendAnimation(caption, content)
            is TdApi.MessageVideo -> sendVideo(caption, content)
            is TdApi.MessageVoiceNote -> sendVoiceNote(caption, content)
            is TdApi.MessageAudio -> sendAudio(caption, content)
            is TdApi.MessageVideoNote -> sendVideoNote(content)
            else -> null
        }
    }

    fun sendMessage(request: TdMessageRequest<*>) {
        val chat = getChat(request.chatId!!)

        val sender = request.update.message.senderId.let { sender ->
            when (sender) {
                is TdApi.MessageSenderChat -> if (sender.chatId != chat.id) {
                    getChat(sender.chatId)
                } else chat
                is TdApi.MessageSenderUser -> if (sender.userId != chat.id) {
                    getUser(sender.userId)
                } else chat
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

    onAnyMessage(isIncoming, isNotChat { toChatId }) {
        sendMessage(request)
    }
}