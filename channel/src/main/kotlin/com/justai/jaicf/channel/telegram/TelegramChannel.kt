package com.justai.jaicf.channel.telegram

import com.github.kotlintelegrambot.bot
import com.github.kotlintelegrambot.dispatch
import com.github.kotlintelegrambot.dispatcher.*
import com.github.kotlintelegrambot.entities.Update
import com.google.gson.Gson
import com.justai.jaicf.api.BotApi
import com.justai.jaicf.channel.http.HttpBotRequest
import com.justai.jaicf.channel.invocationapi.InvocableBotChannel
import com.justai.jaicf.channel.invocationapi.InvocationRequest
import com.justai.jaicf.channel.invocationapi.getRequestTemplateFromResources
import com.justai.jaicf.context.RequestContext
import com.justai.jaicf.helpers.http.withTrailingSlash
import com.justai.jaicf.helpers.kotlin.PropertyWithBackingField
import java.util.*
import java.util.concurrent.TimeUnit

class TelegramChannel(
    val botApi: BotApi,
    private val telegramBotToken: String,
    private val telegramApiUrl: String = "https://api.telegram.org/"
) : InvocableBotChannel {

    private val gson = Gson()

    val bot = bot {
        apiUrl = telegramApiUrl.withTrailingSlash()
        token = telegramBotToken

        dispatch {
            text {
                process(TelegramTextRequest(update, message))
            }

            callbackQuery {
                val message = callbackQuery.message ?: return@callbackQuery
                process(TelegramQueryRequest(update, message, callbackQuery.data))
            }

            location {
                process(TelegramLocationRequest(update, message, location))
            }

            contact {
                process(TelegramContactRequest(update, message, contact))
            }

            audio {
                process(TelegramAudioRequest(update, message, media))
            }

            document {
                process(TelegramDocumentRequest(update, message, media))
            }

            animation {
                process(TelegramAnimationRequest(update, message, media))
            }

            game {
                process(TelegramGameRequest(update, message, media))
            }

            photos {
                process(TelegramPhotosRequest(update, message, media))
            }

            sticker {
                process(TelegramStickerRequest(update, message, media))
            }

            video {
                process(TelegramVideoRequest(update, message, media))
            }

            videoNote {
                process(TelegramVideoNoteRequest(update, message, media))
            }

            voice {
                process(TelegramVoiceRequest(update, message, media))
            }

            preCheckoutQuery {
                process(TelegramPreCheckoutRequest(update, preCheckoutQuery))
            }

            webAppData {
                process(TelegramWebAppDataRequest(update, message, data))
            }
        }
    }

    private fun process(request: TelegramBotRequest) {
        botApi.process(request, TelegramReactions(bot, request, null), RequestContext.fromHttp(request.update.httpBotRequest))
    }

    private fun generateRequestFromTemplate(request: InvocationRequest) =
        getRequestTemplateFromResources(request, REQUEST_TEMPLATE_PATH)
            .replace("\"{{ timestamp }}\"", TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()).toString())
            .replace("{{ messageId }}", UUID.randomUUID().toString())


    override fun processInvocation(request: InvocationRequest, requestContext: RequestContext) {
        val generatedRequest = generateRequestFromTemplate(request)
        val update = gson.fromJson(generatedRequest, Update::class.java) ?: return
        val message = update.message ?: return
        val telegramRequest = TelegramInvocationRequest.create(request, update, message) ?: return
        botApi.process(telegramRequest, TelegramReactions(bot, telegramRequest, null), requestContext)
    }

    fun run() {
        bot.startPolling()
    }

    fun stop() {
        bot.stopPolling()
    }

    companion object {
        private const val REQUEST_TEMPLATE_PATH = "/TelegramRequestTemplate.json"
    }
}

internal var Update.httpBotRequest: HttpBotRequest by PropertyWithBackingField {
    HttpBotRequest("".byteInputStream())
}
