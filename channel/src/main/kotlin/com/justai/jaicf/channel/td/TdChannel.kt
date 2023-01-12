package com.justai.jaicf.channel.td

import com.justai.jaicf.BotEngine
import com.justai.jaicf.channel.BotChannel
import com.justai.jaicf.channel.invocationapi.InvocableBotChannel
import com.justai.jaicf.channel.invocationapi.InvocationEventRequest
import com.justai.jaicf.channel.invocationapi.InvocationQueryRequest
import com.justai.jaicf.channel.invocationapi.InvocationRequest
import com.justai.jaicf.channel.td.api.TdTelegramApi
import com.justai.jaicf.channel.td.api.messageId
import com.justai.jaicf.channel.td.hook.TdClosedHook
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.channel.td.request.*
import com.justai.jaicf.context.RequestContext
import com.justai.jaicf.helpers.kotlin.ifTrue
import it.tdlight.client.AuthenticationData
import it.tdlight.client.ClientInteraction
import it.tdlight.client.TDLibSettings
import it.tdlight.jni.TdApi
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors

class TdChannel(
    override val botApi: BotEngine,
    private val authenticationData: AuthenticationData,
    settings: TDLibSettings,
    clientInteraction: ClientInteraction? = null,
) : BotChannel, InvocableBotChannel {
    private val processExecutor = Executors.newSingleThreadExecutor()

    val api = TdTelegramApi(TdTelegramClient(settings, clientInteraction))

    init {
        onReady(::ready)
    }

    fun start(wait: Boolean = false) = apply {
        api.client.start(authenticationData)
        wait.ifTrue { api.client.waitForExit() }
    }

    fun close(wait: Boolean = false) = apply {
        when (wait) {
            true -> api.client.closeAndWait()
            else -> api.client.sendClose()
        }
    }

    fun onReady(handler: (client: TdTelegramApi) -> Unit): TdChannel = apply {
        api.onUpdate<TdApi.UpdateAuthorizationState> { state ->
            if (state.authorizationState is TdApi.AuthorizationStateReady) {
                handler(api)
            }
        }
    }

    fun onClose(handler: (api: TdTelegramApi) -> Unit): TdChannel = apply {
        api.onUpdate<TdApi.UpdateAuthorizationState> { state ->
            if (state.authorizationState is TdApi.AuthorizationStateClosed) {
                handler(api)
            }
        }
    }

    fun onException(handler: (Throwable) -> Unit) = apply {
        api.onException(handler)
    }

    private fun ready(api: TdTelegramApi) {
        botApi.hooks.triggerHook(TdReadyHook(api))
        onClose { botApi.hooks.triggerHook(TdClosedHook(it)) }
        api.onUpdates(::process)
    }

    private fun process(update: TdApi.Update, requestContext: RequestContext = RequestContext.DEFAULT) {
        processExecutor.execute {
            if (!api.isSentMessage(update)) {
                val request = update.asRequest
                botApi.process(request, TdReactions(api, request), requestContext)
            }
        }
    }

    override fun processInvocation(request: InvocationRequest, requestContext: RequestContext) {
        when (request) {
            is InvocationEventRequest -> request.asEventUpdate
            is InvocationQueryRequest -> request.asNewMessageUpdate
            else -> null
        }?.let(::process)
    }

    private val TdApi.Update.asRequest: TdRequest<out TdApi.Update>
        get() = when (this) {
            is TdApi.UpdateNewMessage -> when (message.content) {
                is TdApi.MessageText -> TdTextMessageRequest(api.me, this)
                else -> TdNewMessageRequest(api.me, this)
            }
            is TdApi.UpdateNewCallbackQuery -> TdCallbackQueryRequest(api.me, this)
            is TdEventUpdate -> TdEventRequest(api.me, this)
            else -> TdUpdateRequest(api.me, this)
        }
}