package com.tdbot.scenario

import com.github.kotlintelegrambot.entities.ParseMode
import com.justai.jaicf.activator.regex.regex
import com.justai.jaicf.channel.invocationapi.invocationRequest
import com.justai.jaicf.channel.td.client.TdTelegramApi
import com.justai.jaicf.channel.td.scenario.createTdModel
import com.justai.jaicf.channel.td.scenario.onReady
import com.justai.jaicf.channel.td.scenario.onUpdate
import com.justai.jaicf.model.activation.onlyIfInSession
import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.reactions.toState
import com.tdbot.api.TdBotApi
import com.tdbot.api.TdBotScenario
import com.tdbot.api.TdInteractiveScenario
import it.tdlight.jni.TdApi

class CatchContact(
    private val tdBotApi: TdBotApi
) : TdInteractiveScenario {
    private lateinit var telegramApi: TdTelegramApi
    private val usersToCatch = mutableSetOf<TdApi.User>()

    override val helpMarkdownText =
        "Catches the user when they become online. " +
                "\nOnce the user becomes online, TdBot informs you with notification \uD83D\uDD34\n" +
                "\nJust type *catch <CONTACT NAME>* to track the contact status.\n" +
                "\n_Please note that the user should be added to your contacts before._"

    override val model = createTdModel {
        onReady {
            telegramApi = api
        }

        onUpdate<TdApi.UpdateUserStatus> {
            if (request.update.status is TdApi.UserStatusOnline && usersToCatch.any { it.id == request.update.userId }) {
                tdBotApi.sendEvent("CatchContactOnline", request.update.userId.toString())
            }
        }
    }

    override val tdBotScenario = TdBotScenario {
        state("CatchContactOnline") {
            activators {
                event("CatchContactOnline")
            }

            action {
                val userId = request.invocationRequest!!.requestData.toLong()
                usersToCatch.find { it.id == userId }?.let { user ->
                    usersToCatch.remove(user)
                    context.session["user_to_catch"] = user
                    val username = user.usernames.activeUsernames.firstOrNull()
                    if (username != null) {
                        reactions.say("\uD83D\uDD34 [${user.firstName} ${user.lastName}](https://t.me/$username) is online now", ParseMode.MARKDOWN_V2)
                    } else {
                        reactions.say("\uD83D\uDD34 ${user.firstName} ${user.lastName} is online now")
                    }
                    reactions.buttons("Catch later".toState("/CatchContact/catch"))
                }
            }
        }

        state("CatchContact") {
            activators {
                regex("catch (.*)")
            }

            action(regex) {
                val userName = activator.matcher.group(1)
                context.cleanSessionData()

                telegramApi.send(TdApi.SearchContacts(userName, 1)) { res ->
                    if (res.isError || res.get().totalCount == 0) {
                        reactions.say("Sorry, \"$userName\" was not found in your contacts")
                    } else {
                        telegramApi.send(TdApi.GetUser(res.get().userIds.first())) { user ->
                            context.session["user_to_catch"] = user.get()
                            reactions.say("Would you like to catch ${user.get().firstName} ${user.get().lastName}?")
                            reactions.buttons("Yes", "No")
                        }
                    }
                }
            }

            state("catch") {
                activators {
                    regex("yes").onlyIfInSession("user_to_catch")
                }

                action {
                    val user = context.session.remove("user_to_catch") as TdApi.User
                    usersToCatch.add(user)
                    reactions.say("Okay! I'll ping you once ${user.firstName} becomes online.")
                }
            }

            state("no") {
                activators {
                    regex("no|nope").onlyIfInSession("user_to_catch")
                }

                action {
                    context.session.remove("user_to_catch")
                    reactions.say("Okay")
                }
            }
        }
    }
}