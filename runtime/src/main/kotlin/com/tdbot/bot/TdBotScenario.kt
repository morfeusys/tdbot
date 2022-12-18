package com.tdbot.bot

import com.github.kotlintelegrambot.entities.ParseMode
import com.justai.jaicf.activator.regex.regex
import com.justai.jaicf.builder.createModel
import com.justai.jaicf.channel.invocationapi.invocationRequest
import com.justai.jaicf.channel.td.hook.TdReadyHook
import com.justai.jaicf.channel.telegram.telegram
import com.justai.jaicf.context.DialogContext
import com.justai.jaicf.helpers.kotlin.ifTrue
import com.justai.jaicf.hook.BeforeActionHook
import com.justai.jaicf.hook.BotHookException
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.reactions.toState
import com.tdbot.api.TdInteractiveScenario
import com.tdbot.runtime.LinkClientInteraction
import com.tdbot.runtime.TdRuntime
import it.tdlight.jni.TdApi

class TdBotScenario(
    clientInteraction: LinkClientInteraction,
    scenarios: Scenarios,
) : Scenario {
    private var me: TdApi.User? = null

    override val model = createModel(telegram) {
        handle<TdReadyHook> {
            me = user
        }

        handle<BeforeActionHook> {
            if (!context.dialogContext.isAllowed()) {
                if (me == null) {
                    reactions.say("⚠️ You're not logged in Telegram yet.")
                    reactions.go("/TdBotScenario.GetPublicQR")
                }
                if (request.clientId != me?.id.toString()) {
                    throw BotHookException("Access denied")
                }
            }
        }

        state("TdBotScenario.Start") {
            activators {
                regex("/start.*")
            }

            action {
                reactions.say("\uD83D\uDC4B Hello ${request.message.from?.firstName}!")
                reactions.go("/TdBotScenario.Scenarios")
            }
        }

        state("TdBotScenario.onReady") {
            activators {
                event("ready")
            }

            action {
                reactions.say("✋ Okay, ${me?.firstName}! I'm ready now.")
                reactions.go("/TdBotScenario.Scenarios")
            }
        }

        state("TdBotScenario.onClose") {
            activators {
                event("close")
            }

            action {
                me = null
                reactions.say("\uD83D\uDD34 Telegram client was closed.")
                reactions.buttons("Sign in to Telegram" toState "/TdBotScenario.GetPublicQR")
            }
        }

        state("TdBotScenario.GetPublicQR") {
            action {
                if (me != null) {
                    reactions.say("You're logged in already")
                } else {
                    reactions.say("Here is [your public QR link](${clientInteraction.publicUrl}) to sign in\\.",
                        ParseMode.MARKDOWN_V2,
                        disableWebPagePreview = true)
                    reactions.say("_Open it in the browser and follow instructions_", ParseMode.MARKDOWN_V2)
                    reactions.buttons(
                        "Get new public URL" toState "/TdBotScenario.GetPublicQR",
                    )
                }
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
                    val enabled = StringBuilder("You have ${scenarios.enabled.size.scenarios} enabled so far\n")
                    scenarios.enabled.keys.forEach { enabled.append("\n/$it") }
                    reactions.say(enabled.toString())
                    val disabled = StringBuilder("${scenarios.disabled.size.scenarios} ${scenarios.disabled.size.are} disabled\n")
                    scenarios.disabled.keys.forEach { disabled.append("\n/$it") }
                    reactions.say(disabled.toString())
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

        state("TdBotScenario.onMessage", noContext = true) {
            activators {
                event("TdBotScenario.onMessage")
            }

            action {
                reactions.say(request.invocationRequest!!.requestData, ParseMode.MARKDOWN)
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

    companion object {
        private val allowAllStates = setOf("/TdBotScenario.GetPublicQR")

        private fun DialogContext.isAllowed() = allowAllStates.any { currentState.startsWith(it) }
    }
}