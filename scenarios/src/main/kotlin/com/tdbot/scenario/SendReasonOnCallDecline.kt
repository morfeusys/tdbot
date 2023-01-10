package com.tdbot.scenario

import com.github.kotlintelegrambot.entities.ParseMode
import com.justai.jaicf.channel.td.api.TdTelegramApi
import com.justai.jaicf.channel.td.api.sendTextMessage
import com.justai.jaicf.channel.td.scenario.createTdModel
import com.justai.jaicf.channel.td.scenario.onReady
import com.justai.jaicf.channel.td.scenario.onUpdateCall
import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.reactions.toState
import com.tdbot.api.createInteractiveScenario
import com.tdbot.api.TdInteractiveScenario
import com.tdbot.api.event
import com.tdbot.scenario.utils.asEmojiUnicode
import it.tdlight.jni.TdApi

class SendReasonOnCallDecline(vararg reasons: String) : TdInteractiveScenario() {
    private lateinit var telegramApi: TdTelegramApi

    private val buttons = reasons.map {
        it.take(21).replaceRange(18..20, "...") toState "/SendReasonText/$it"
    }.toTypedArray()

    override val model = createTdModel {
        onReady {
            telegramApi = api
        }

        onUpdateCall {
            val state = request.update.call.state
            if (state is TdApi.CallStateDiscarded && state.reason is TdApi.CallDiscardReasonEmpty) {
                sendInteractiveScenarioEvent("SendReasonOnCallDecline", request.clientId)
            }
        }
    }

    override val interactiveScenario = createInteractiveScenario {
        event("SendReasonOnCallDecline") { userId ->
            val user = telegramApi.send(TdApi.GetUser(userId.toLong()))

            context.session["user_to_send_call_dismiss_reason"] = user

            reactions.say("${"telephone_receiver".asEmojiUnicode} You've declined incoming call from *${user.firstName} ${user.lastName}*.\n\n"
                    + "Click one of the buttons below to send a reason text back to them.", ParseMode.MARKDOWN)
            reactions.buttons(*buttons)
        }

        reasons.forEach { reason ->
            state("/SendReasonText/$reason") {
                action {
                    val user = context.session.remove("user_to_send_call_dismiss_reason") as TdApi.User
                    telegramApi.sendTextMessage(user.id, reason)
                    reactions.say("Okay, I've sent a reason _\"$reason\"_ to *${user.firstName} ${user.lastName}*.", ParseMode.MARKDOWN)
                }
            }
        }
    }

}