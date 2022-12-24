package com.tdbot.bot.scenario

import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.ReplyKeyboardRemove
import com.justai.jaicf.activator.regex.regex
import com.justai.jaicf.builder.createModel
import com.justai.jaicf.channel.invocationapi.invocationRequest
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.channel.telegram.telegram
import com.justai.jaicf.helpers.kotlin.ifTrue
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.reactions.toState
import com.tdbot.api.TdInteractiveScenario
import com.tdbot.bot.Scenarios
import com.tdbot.runtime.AuthService
import it.tdlight.jni.TdApi

class TdBotScenario(
    authService: AuthService,
    scenarios: Scenarios,
) : Scenario {
    private var me: TdApi.User? = null

    override val model = createModel(telegram) {
        append(SignInScenario(authService))

        handle<TdReadyHook> {
            me = user
        }

        state("TdBotScenario.Start") {
            activators {
                regex("/start.*")
            }


            action {
                reactions.say("\uD83D\uDC4B Hello ${request.message.from?.firstName}!", replyMarkup = ReplyKeyboardRemove())
                reactions.go("/TdBotScenario.Scenarios")
            }
        }

        state("TdBotScenario.onReady") {
            activators {
                event("ready")
            }

            action {
                reactions.say("✋ Okay, ${me?.firstName}! I'm ready now.", replyMarkup = ReplyKeyboardRemove())
                reactions.go("/TdBotScenario.Scenarios")
            }
        }

        state("TdBotScenario.onClose") {
            activators {
                event("close")
            }

            action {
                reactions.say("\uD83D\uDD34 Telegram client was closed.")
                reactions.buttons("Sign in to Telegram" toState "/TdBotScenario.SignIn")
            }
        }

        state("TdBotScenario.Scenarios") {
            activators {
                regex("/?scenarios")
            }

            action {
                if (scenarios.all.isEmpty()) {
                    reactions.say("You don't have any scenarios yet...")
                } else {
                    if (scenarios.enabled.isNotEmpty()) {
                        val enabled = StringBuilder("You have ${scenarios.enabled.size.scenarios} enabled so far\n")
                        scenarios.enabled.keys.forEach { enabled.append("\n/$it") }
                        reactions.say(enabled.toString())
                    }
                    if (scenarios.disabled.isNotEmpty()) {
                        val disabled = StringBuilder("${scenarios.disabled.size.scenarios} ${scenarios.disabled.size.are} disabled\n")
                        scenarios.disabled.keys.forEach { disabled.append("\n/$it") }
                        reactions.say(disabled.toString())
                    }
                }
            }
        }

        state("TdBotScenario.Scenario") {
            activators {
                scenarios.all.keys.forEach {
                    regex("/($it)")
                }
            }

            action(regex) {
                val scenario = activator.matcher.group(1)
                val enabled = scenarios.isEnabled(scenario)
                context.session["selected_scenario"] = scenario
                reactions.say("$scenario scenario is ${enabled.status} now.")
                reactions.buttons((enabled.ifTrue { "Disable" } ?: "Enable").toState("toggle"))
                if (scenarios.isInteractive(scenario)) {
                    reactions.buttons("How to use".toState("help"))
                }
            }

            state("toggle") {
                action {
                    val scenario = context.session.remove("selected_scenario") as String
                    val enabled = scenarios.toggle(scenario)
                    reactions.say("$scenario scenario is ${enabled.status} now.")
                    reactions.go("/TdBotScenario.Scenarios")
                }
            }

            state("help") {
                action {
                    val scenario = context.session["selected_scenario"] as String
                    val enabled = scenarios.isEnabled(scenario)
                    scenarios.all[scenario]?.let {
                        reactions.say("Here is how the $scenario scenario works")
                        reactions.say((it as TdInteractiveScenario).helpMarkdownText, ParseMode.MARKDOWN)
                    }
                    reactions.buttons((enabled.ifTrue { "Disable" } ?: "Enable").toState("../toggle"))
                }
            }
        }

        scenarios.all.values.filterIsInstance(TdInteractiveScenario::class.java).forEach {
            append(it.tdBotScenario)
        }

    }

    private val Boolean.status
        get() = ifTrue { "enabled" } ?: "disabled"

    private val Int.scenarios
        get() = "$this scenario${(this == 1).ifTrue { "" } ?: "s"}"

    private val Int.are
        get() = (this == 1).ifTrue { "is" } ?: "are"
}