package com.tdbot.bot.scenario

import com.github.kotlintelegrambot.entities.KeyboardReplyMarkup
import com.github.kotlintelegrambot.entities.ReplyKeyboardRemove
import com.github.kotlintelegrambot.entities.keyboard.KeyboardButton
import com.github.kotlintelegrambot.entities.keyboard.WebAppInfo
import com.justai.jaicf.builder.Scenario
import com.justai.jaicf.channel.invocationapi.invocationRequest
import com.justai.jaicf.channel.td.hook.TdClosedHook
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.channel.telegram.TelegramEvent
import com.justai.jaicf.channel.telegram.contact
import com.justai.jaicf.channel.telegram.telegram
import com.justai.jaicf.channel.telegram.webAppData
import com.justai.jaicf.hook.BeforeActionHook
import com.justai.jaicf.hook.BotHookException
import com.tdbot.runtime.AuthService
import it.tdlight.jni.TdApi

fun SignInScenario(authService: AuthService) = Scenario(telegram) {
    val allowAllStates = setOf("/TdBotScenario.SignIn")

    var me: TdApi.User? = null

    handle<TdReadyHook> {
        me = user
    }

    handle<TdClosedHook> {
        me = null
    }

    handle<BeforeActionHook> {
        if (allowAllStates.none { context.dialogContext.currentState.startsWith(it) }) {
            if (me == null) {
                reactions.say("⚠️ You're not logged in Telegram yet.")
                reactions.go("/TdBotScenario.SignIn")
            }
            if (request.clientId != me?.id.toString()) {
                throw BotHookException("Access is denied")
            }
        }
    }

    state("TdBotScenario.SignIn") {
        action {
            if (me != null) {
                reactions.say("You're logged in already")
            } else {
                reactions.say("Share your contact to sign in", replyMarkup = KeyboardReplyMarkup(
                    KeyboardButton("Share my contact", requestContact = true)
                ))
            }
        }

        state("Contact") {
            activators {
                event(TelegramEvent.CONTACT)
            }

            action(telegram.contact) {
                if (request.contact.userId != request.chatId) {
                    reactions.say("Send me your own contact, not anther's one")
                } else {
                    val phone = request.contact.phoneNumber.let { p -> when {
                        p.startsWith("+") -> p.substring(1)
                        else -> p
                    } }.toLong()

                    authService.setPhoneNumber(request.chatId, phone)

                    reactions.say(
                        text = "If Telegram was sent you a confirmation code, click on the button below and enter the code.",
                        replyMarkup = KeyboardReplyMarkup(
                            KeyboardButton(
                                text ="Enter code",
                                webApp = WebAppInfo("https://tdbot.vercel.app/")
                            ))
                    )
                }
            }

            state("Data") {
                activators {
                    event(TelegramEvent.WEB_APP_DATA)
                }

                action(telegram.webAppData) {
                    authService.setConfirmationCode(request.data.data)
                }
            }

            state("Error") {
                activators {
                    event("error")
                }

                action {
                    reactions.say("⚠️ Authentication error. ${request.invocationRequest?.requestData}")
                }
            }
        }
    }
}
