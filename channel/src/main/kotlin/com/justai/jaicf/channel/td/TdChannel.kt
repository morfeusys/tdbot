package com.justai.jaicf.channel.td

import com.justai.jaicf.BotEngine
import com.justai.jaicf.channel.BotChannel
import com.justai.jaicf.channel.td.client.TdTelegramApi
import com.justai.jaicf.channel.td.hook.TdClosedHook
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.context.RequestContext
import com.justai.jaicf.helpers.kotlin.ifTrue
import it.tdlight.client.AuthenticationData
import it.tdlight.client.ClientInteraction
import it.tdlight.client.SimpleTelegramClient
import it.tdlight.client.TDLibSettings
import it.tdlight.common.ExceptionHandler
import it.tdlight.jni.TdApi

class TdChannel(
    override val botApi: BotEngine,
    private val authenticationData: AuthenticationData,
    settings: TDLibSettings,
    clientInteraction: ClientInteraction? = null,
) : BotChannel {
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
        api.client.addUpdateHandler(TdApi.UpdateAuthorizationState::class.java) { state ->
            if (state.authorizationState is TdApi.AuthorizationStateReady) {
                handler.invoke(api)
            }
        }
    }

    fun onClose(handler: (api: TdTelegramApi) -> Unit): TdChannel = apply {
        api.client.addUpdateHandler(TdApi.UpdateAuthorizationState::class.java) { state ->
            if (state.authorizationState is TdApi.AuthorizationStateClosed) {
                handler.invoke(api)
            }
        }
    }

    fun onException(handler: (e: Throwable) -> Unit) = apply {
        api.client.addDefaultExceptionHandler(handler)
    }

    private fun ready(client: TdTelegramApi) {
        client.send(TdApi.GetMe()) { res ->
            if (res.error().isPresent) {
                throw IllegalStateException(res.error.message)
            } else {
                botApi.hooks.triggerHook(TdReadyHook(client, res.get()))
                addHandlers(res.get())
            }
        }
    }

    private fun addHandlers(me: TdApi.User) {
        api.client.addUpdatesHandler { update ->
            val request = when (update) {
                is TdApi.UpdateNewMessage -> when (update.message.content) {
                    is TdApi.MessageText -> TdNewTextMessageRequest(me, update)
                    else -> TdNewEventMessageRequest(me, update)
                }
                else -> TdUpdateRequest(me, update)
            }

            botApi.process(request, TdReactions(api, request), RequestContext.DEFAULT)
        }

        onClose { client ->
            botApi.hooks.triggerHook(TdClosedHook(client, me))
        }
    }
}