package com.justai.jaicf.channel.td

import com.justai.jaicf.BotEngine
import com.justai.jaicf.channel.BotChannel
import com.justai.jaicf.channel.td.api.TdTelegramApi
import com.justai.jaicf.channel.td.api.messageId
import com.justai.jaicf.channel.td.hook.TdClosedHook
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.context.RequestContext
import com.justai.jaicf.helpers.kotlin.ifTrue
import it.tdlight.client.AuthenticationData
import it.tdlight.client.ClientInteraction
import it.tdlight.client.SimpleTelegramClient
import it.tdlight.client.TDLibSettings
import it.tdlight.jni.TdApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import org.slf4j.LoggerFactory

class TdChannel(
    override val botApi: BotEngine,
    private val authenticationData: AuthenticationData,
    settings: TDLibSettings,
    clientInteraction: ClientInteraction? = null,
) : BotChannel {
    private val botProcessScope = CoroutineScope(newSingleThreadContext("TdChannel process thread"))

    private val api = TdTelegramApi(
        SimpleTelegramClient(settings).apply {
            clientInteraction?.let { setClientInteraction(it) }
        }
    )

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
        addHandlers()
    }

    private fun addHandlers() {
        api.onUpdates { update ->
            botProcessScope.launch {
                if (api.sentMessages.none { it.id == update.messageId }) {
                    val request = when (update) {
                        is TdApi.UpdateNewMessage -> when (update.message.content) {
                            is TdApi.MessageText -> TdTextMessageRequest(api.me, update)
                            else -> TdEventMessageRequest(api.me, update)
                        }
                        else -> TdUpdateRequest(api.me, update)
                    }
                    botApi.process(request, TdReactions(api, request), RequestContext.DEFAULT)
                }
            }
        }

        onClose {
            botApi.hooks.triggerHook(TdClosedHook(it))
        }
    }
}