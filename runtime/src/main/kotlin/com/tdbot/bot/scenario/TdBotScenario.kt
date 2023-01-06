package com.tdbot.bot.scenario

import com.github.kotlintelegrambot.entities.ParseMode
import com.github.kotlintelegrambot.entities.ReplyKeyboardRemove
import com.justai.jaicf.activator.regex.regex
import com.justai.jaicf.builder.createModel
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.channel.telegram.telegram
import com.justai.jaicf.helpers.kotlin.ifTrue
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.reactions.ButtonToState
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
                    regex("/?($it)")
                }
            }

            action(regex) {
                val name = activator.matcher.group(1)
                val enabled = scenarios.isEnabled(name)
                val buttons = mutableListOf((enabled.ifTrue { "Disable" } ?: "Enable").toState("toggle"))

                context.session["selected_scenario"] = name
                reactions.say("$name scenario is ${enabled.status} now.")

                if (scenarios.isInteractive(name)) {
                    val scenario = scenarios.all[name] as TdInteractiveScenario
                    if (scenario.config.helpMarkdownText?.isNotBlank() == true) {
                        buttons.add("How to use" toState "help")
                    }
                    scenario.config.startButton?.also(buttons::add)
                }

                reactions.buttons(*buttons.toTypedArray())

            }

            state("toggle") {
                action {
                    val scenario = context.session.remove("selected_scenario") as String
                    val enabled = scenarios.toggle(scenario)
                    reactions.say("_${scenario}_ scenario is ${enabled.status} now.", ParseMode.MARKDOWN)
                    reactions.go("/TdBotScenario.Scenarios")
                }
            }

            state("help") {
                action {
                    val name = context.session["selected_scenario"] as String
                    val scenario = scenarios.all[name] as TdInteractiveScenario
                    val enabled = scenarios.isEnabled(name)
                    reactions.say("Here is how the _${name}_ scenario works\n\n" +
                            scenario.config.helpMarkdownText!!, ParseMode.MARKDOWN)

                    val buttons = mutableListOf((enabled.ifTrue { "Disable" } ?: "Enable").toState("../toggle"))
                    scenario.config.startButton?.also(buttons::add)
                    reactions.buttons(*buttons.toTypedArray())
                }
            }
        }

        scenarios.interactive.forEach {
            append(it.interactiveScenario)
        }

    }

    private val Boolean.status
        get() = ifTrue { "enabled" } ?: "disabled"

    private val Int.scenarios
        get() = "$this scenario${(this == 1).ifTrue { "" } ?: "s"}"

    private val Int.are
        get() = (this == 1).ifTrue { "is" } ?: "are"
}