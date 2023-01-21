package com.justai.gramlin.bot.scenario

import com.justai.jaicf.activator.regex.regex
import com.justai.jaicf.channel.td.TdMessage
import com.justai.jaicf.channel.td.TdReactions
import com.justai.jaicf.channel.td.request.td
import com.justai.jaicf.channel.td.request.updateNewCallbackQueryRequest
import com.justai.jaicf.channel.td.scenario.createTdModel
import com.justai.jaicf.helpers.kotlin.ifTrue
import com.justai.jaicf.hook.BotRequestHook
import com.justai.jaicf.model.scenario.Scenario
import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.reactions.toState
import com.justai.gramlin.api.GramlinInteractiveScenario
import com.justai.gramlin.bot.Scenarios
import com.justai.gramlin.runtime.AuthService
import it.tdlight.jni.TdApi

class GramlinBotScenario(
    authService: AuthService,
    scenarios: Scenarios,
) : Scenario {
    override val model = createTdModel {
        append(SignInScenario(authService))

        handle<BotRequestHook> {
            request.td?.updateNewCallbackQueryRequest?.also { query ->
                val tdReactions = reactions as TdReactions
                tdReactions.api.send(TdApi.AnswerCallbackQuery(query.update.id, null, false, null, 0))
            }
        }

        state("GramlinBotScenario.Start") {
            activators {
                regex("/?start.*")
                regex("restart")
            }

            action {
                reactions.sendText("\uD83D\uDC4B Hello!", replyMarkup = TdApi.ReplyMarkupRemoveKeyboard())
                reactions.go("/GramlinBotScenario.Scenarios")
            }
        }

        state("GramlinBotScenario.onReady") {
            action {
                reactions.sendText("✋ Okay, I'm ready now.", replyMarkup = TdApi.ReplyMarkupRemoveKeyboard())
                reactions.go("/GramlinBotScenario.Scenarios")
            }
        }

        state("GramlinBotScenario.Scenarios") {
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

        state("GramlinBotScenario.Scenario") {
            activators {
                scenarios.all.keys.forEach {
                    regex("/?($it)")
                }
            }

            action(regex) {
                scenarios.findScenarioName(activator.matcher.group(1))?.also { name ->
                    val enabled = scenarios.isEnabled(name)
                    val buttons = mutableListOf((enabled.ifTrue { "Disable" } ?: "Enable").toState("toggle"))

                    context.session["selected_scenario"] = name
                    reactions.sendText("_${name}_ scenario is _${enabled.status}_ now.", TdMessage.ParseMode.Markdown)

                    if (scenarios.isInteractive(name)) {
                        val scenario = scenarios.all[name] as GramlinInteractiveScenario
                        if (scenario.config.helpMarkdownText?.isNotBlank() == true) {
                            buttons.add("How to use" toState "help")
                        }
                        buttons.addAll(scenario.config.actionButtons)
                    }

                    reactions.buttons(*buttons.toTypedArray())
                }
            }

            state("toggle") {
                action {
                    val scenario = context.session.remove("selected_scenario") as String
                    val enabled = scenarios.toggle(scenario)
                    reactions.sendText("_${scenario}_ scenario is ${enabled.status} now.", TdMessage.ParseMode.Markdown)
                    reactions.go("/GramlinBotScenario.Scenarios")
                }
            }

            state("help") {
                action {
                    val name = context.session["selected_scenario"] as String
                    val scenario = scenarios.all[name] as GramlinInteractiveScenario
                    val enabled = scenarios.isEnabled(name)
                    reactions.sendText("Here is how the _${name}_ scenario works\n\n" +
                            scenario.config.helpMarkdownText!!, TdMessage.ParseMode.Markdown)

                    val buttons = mutableListOf((enabled.ifTrue { "Disable" } ?: "Enable").toState("../toggle"))
                    buttons.addAll(scenario.config.actionButtons)
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

    companion object {
        const val EVENT_READY = "GramlinBotScenario.onReady"
        const val EVENT_CLOSE = "GramlinBotScenario.onClose"
        const val EVENT_ERROR = "GramlinBotScenario.onError"
    }
}