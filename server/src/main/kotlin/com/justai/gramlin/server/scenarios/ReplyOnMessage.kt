package com.justai.gramlin.server.scenarios

import com.justai.jaicf.channel.td.TdMessage
import com.justai.jaicf.channel.td.api.asLocalFile
import com.justai.gramlin.api.TdMessageContentType
import com.justai.gramlin.api.TdMessageContentType.*
import com.justai.gramlin.scenario.ReactOnMessage
import com.justai.gramlin.server.ScenarioFactory
import com.justai.gramlin.server.executeTemplate
import java.io.File

data class ReplyOnMessageConfig(
    val message: Message,
    val reply: Reply,
) {
    data class Message(
        val pattern: String,
        val types: List<TdMessageContentType> = emptyList()
    )

    data class Reply(
        val content: String,
        val type: TdMessageContentType = Text
    )
}

val ReplyOnMessage =
    ScenarioFactory.create<ReplyOnMessageConfig>("reply_on_message.yaml") { config ->
        ReactOnMessage(config.message.pattern.executeTemplate(), config.message.types.toTypedArray()) {
            val content = config.reply.content.executeTemplate(request)
            when (config.reply.type) {
                Text -> TdMessage.text(content)
                Photo -> TdMessage.photo(content)
                Animation -> TdMessage.animation(content)
                Audio -> TdMessage.audio(content)
                Video -> TdMessage.video(content)
                VideoNote -> TdMessage.videoNote(content)
                VoiceNote -> TdMessage.voiceNote(content)
                Sticker -> TdMessage.sticker(content)
                Document -> TdMessage.document(File(content).asLocalFile)
                else -> null
            }?.also(reactions::reply)
        }
    }