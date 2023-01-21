package com.justai.gramlin.bot.scenario

import com.justai.jaicf.api.hasEvent
import com.justai.jaicf.channel.invocationapi.invocationRequest
import com.justai.jaicf.channel.td.TdMessage
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.tdContactMessage
import com.justai.jaicf.channel.td.scenario.tdContactMessageType
import com.justai.jaicf.hook.BeforeActionHook
import com.justai.jaicf.hook.BotHookException
import com.justai.gramlin.runtime.AuthService
import com.justai.jaicf.channel.td.hook.TdClosedHook
import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.reactions.toState
import it.tdlight.jni.TdApi

fun SignInScenario(authService: AuthService) = TdScenario {
    val allowAllStates = setOf("/GramlinBotScenario.SignIn")
    var userId: Long? = null

    handle<BeforeActionHook> {
        if (!request.hasEvent() && allowAllStates.none { context.dialogContext.currentState.startsWith(it) }) {
            if (userId == null) {
                reactions.say("⚠️ You're not logged in Telegram yet.")
                reactions.go("/GramlinBotScenario.SignIn")
            }
            if (request.clientId != userId?.toString()) {
                throw BotHookException("Access is denied")
            }
        }
    }

    state("GramlinBotScenario.Authenticated") {
        activators {
            event(GramlinBotScenario.EVENT_READY)
        }

        action {
            userId = request.invocationRequest?.requestData?.toLong()?.also {
                reactions.go("/GramlinBotScenario.onReady")
            }
        }
    }

    state("GramlinBotScenario.signedOut") {
        activators {
            event(GramlinBotScenario.EVENT_CLOSE)
        }

        action {
            userId = null
            reactions.say("\uD83D\uDD34 Telegram client was closed.")
            reactions.buttons("Sign in to Telegram" toState "/GramlinBotScenario.SignIn")
        }
    }

    state("GramlinBotScenario.SignIn") {
        action {
            if (userId != null) {
                reactions.say("You're logged in already")
            } else {
                reactions.sendText("Share your contact to sign in",
                    replyMarkup = TdMessage.phoneKeyboard("Share my contact"))
            }
        }

        state("Contact") {
            activators {
                tdContactMessage()
            }

            action(tdContactMessageType) {
                val contact = request.content.contact
                val message = request.update.message
                if (contact.userId != message.chatId) {
                    reactions.say("Send me your own contact, not anther's one")
                } else {
                    val phone = contact.phoneNumber.let { p -> when {
                        p.startsWith("+") -> p.substring(1)
                        else -> p
                    } }.toLong()

                    authService.setPhoneNumber(message.chatId, phone)

                    reactions.sendText("Telegram was sent you a confirmation code.\n" +
                            "Send me this code *with spaces between digits.*\n\n" +
                            "_For example: 4 5 6 7 8, not 45678._", TdMessage.ParseMode.Markdown)
                    reactions.sendText("⚠️ NOTE that code without spaces will be rejected by Telegram!",
                        replyMarkup = TdApi.ReplyMarkupRemoveKeyboard())
                }
            }

            state("Code") {
                activators {
                    regex("([0-9]\\s+)+[0-9]")
                }

                action {
                    val code = request.input.replace(" ", "").trim()
                    authService.setConfirmationCode(code)
                }
            }

            state("Error") {
                activators {
                    event(GramlinBotScenario.EVENT_ERROR)
                }

                action {
                    reactions.say("⚠️ Authentication error. ${request.invocationRequest?.requestData}")
                }
            }
        }
    }
}
