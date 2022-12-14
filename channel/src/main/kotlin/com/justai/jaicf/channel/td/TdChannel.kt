package com.justai.jaicf.channel.td

import com.justai.jaicf.BotEngine
import com.justai.jaicf.channel.BotChannel
import com.justai.jaicf.channel.td.hook.TdClosedHook
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.context.RequestContext
import com.justai.jaicf.helpers.kotlin.ifTrue
import it.tdlight.client.AuthenticationData
import it.tdlight.client.ClientInteraction
import it.tdlight.client.SimpleTelegramClient
import it.tdlight.client.TDLibSettings
import it.tdlight.jni.TdApi

class TdChannel(
    override val botApi: BotEngine,
    private val authenticationData: AuthenticationData,
    settings: TDLibSettings,
    clientInteraction: ClientInteraction? = null,
) : BotChannel {
    private val client = SimpleTelegramClient(settings).apply {
        clientInteraction?.let { setClientInteraction(it) }
    }

    init {
        onReady(::ready)
    }

    fun start(wait: Boolean = false) = apply {
        client.start(authenticationData)
        wait.ifTrue { client.waitForExit() }
    }

    fun onReady(handler: (client: SimpleTelegramClient) -> Unit): TdChannel = apply {
        client.addUpdateHandler(TdApi.UpdateAuthorizationState::class.java) { state ->
            if (state.authorizationState is TdApi.AuthorizationStateReady) {
                handler.invoke(client)
            }
        }
    }

    fun onClose(handler: (client: SimpleTelegramClient) -> Unit): TdChannel = apply {
        client.addUpdateHandler(TdApi.UpdateAuthorizationState::class.java) { state ->
            if (state.authorizationState is TdApi.AuthorizationStateClosed) {
                handler.invoke(client)
            }
        }
    }

    private fun ready(client: SimpleTelegramClient) {
        client.send(TdApi.GetMe()) { res ->
            if (res.error().isPresent) {
                throw IllegalStateException(res.error.message)
            } else {
                botApi.hooks.triggerHook(TdReadyHook(client, res.get()))
                addHandlers(res.get())
            }
        }
    }

    private fun addHandlers(user: TdApi.User) {
        client.addUpdatesHandler { update ->
            val request = when (update) {
                is TdApi.UpdateNewMessage -> when (update.message.content) {
                    is TdApi.MessageText -> TdNewTextMessageRequest(user, update, update.message.content as TdApi.MessageText)
                    else -> TdNewEventMessageRequest(user, update)
                }
                else -> TdUpdateRequest(user, update)
            }

            botApi.process(request, TdReactions(client, request), RequestContext.DEFAULT)
        }

        onClose { client ->
            botApi.hooks.triggerHook(TdClosedHook(client, user))
        }
    }
}