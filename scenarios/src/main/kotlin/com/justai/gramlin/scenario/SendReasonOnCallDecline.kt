package com.justai.gramlin.scenario

import com.justai.jaicf.channel.td.TdMessage
import com.justai.jaicf.channel.td.api.TdTelegramApi
import com.justai.jaicf.channel.td.api.sendMessage
import com.justai.jaicf.channel.td.scenario.createTdModel
import com.justai.jaicf.channel.td.scenario.createTdScenario
import com.justai.jaicf.channel.td.scenario.onReady
import com.justai.jaicf.channel.td.scenario.onUpdateCall
import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.reactions.toState
import com.justai.gramlin.api.GramlinInteractiveScenario
import com.justai.gramlin.api.event
import com.justai.gramlin.scenario.utils.asInlineButtonTitle
import com.justai.gramlin.scenario.utils.emoji_telephone_receiver
import it.tdlight.jni.TdApi

class SendReasonOnCallDecline(vararg reasons: String) : GramlinInteractiveScenario() {
    private lateinit var telegramApi: TdTelegramApi

    private val buttons = reasons.mapIndexed {
            index, s -> s.asInlineButtonTitle toState "/SendReasonText/$index"
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

    override val interactiveScenario = createTdScenario {
        event("SendReasonOnCallDecline") { userId ->
            val user = telegramApi.send(TdApi.GetUser(userId.toLong()))
            context.session["user_to_send_call_dismiss_reason"] = user
            reactions.sendText("$emoji_telephone_receiver You've declined incoming call from *${user.firstName} ${user.lastName}*.\n\n"
                    + "Click one of the buttons below to send a reason text back to them.", TdMessage.ParseMode.Markdown)
            reactions.buttons(*buttons)
        }

        reasons.forEachIndexed { index, reason ->
            state("/SendReasonText/$index") {
                action {
                    val user = context.session.remove("user_to_send_call_dismiss_reason") as TdApi.User
                    telegramApi.sendMessage(user.id, content = TdMessage.text(reason))
                    reactions.sendText("Okay, I've sent a reason _\"$reason\"_ to *${user.firstName} ${user.lastName}*.", TdMessage.ParseMode.Markdown)
                }
            }
        }
    }

}