package com.tdbot.scenario

import com.github.kotlintelegrambot.entities.ParseMode
import com.justai.jaicf.activator.regex.regex
import com.justai.jaicf.channel.td.client.TdTelegramApi
import com.justai.jaicf.channel.td.isClients
import com.justai.jaicf.channel.td.scenario.createTdModel
import com.justai.jaicf.channel.td.scenario.onReady
import com.justai.jaicf.channel.td.scenario.onUpdateUserStatus
import com.justai.jaicf.model.activation.onlyIfInSession
import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.reactions.toState
import com.tdbot.api.createInteractiveScenario
import com.tdbot.api.TdInteractiveScenario
import com.tdbot.api.event
import com.tdbot.scenario.utils.asEmojiUnicode
import it.tdlight.jni.TdApi

object CatchContact : TdInteractiveScenario({
    helpMarkdownText = "Catches the user when they become online. " +
            "\nOnce the user becomes online, TdBot informs you with notification ${"red_circle".asEmojiUnicode}\n" +
            "\nJust type *catch <CONTACT NAME>* to track the contact status.\n" +
            "\n_Please note that the user should be added to your contacts before._"
}) {
    private lateinit var telegramApi: TdTelegramApi
    private val usersToCatch = mutableSetOf<TdApi.User>()

    override val model = createTdModel {
        onReady {
            telegramApi = api
        }

        onUpdateUserStatus(isClients { usersToCatch.map { it.id.toString() } }) {
            if (request.update.status is TdApi.UserStatusOnline) {
                sendInteractiveScenarioEvent("CatchContactOnline", request.clientId)
            }
        }
    }

    override val interactiveScenario = createInteractiveScenario {
        event("CatchContactOnline") { data ->
            val userId = data.toLong()
            usersToCatch.find { it.id == userId }?.let { user ->
                usersToCatch.remove(user)
                context.session["user_to_catch"] = user
                val username = user.usernames.activeUsernames.firstOrNull()
                if (username != null) {
                    reactions.say(
                        "${"red_circle".asEmojiUnicode} [${user.firstName} ${user.lastName}](https://t.me/$username) is online now",
                        ParseMode.MARKDOWN_V2
                    )
                } else {
                    reactions.say("${"red_circle".asEmojiUnicode} ${user.firstName} ${user.lastName} is online now")
                }
                reactions.buttons("Catch later".toState("/CatchContact/catch"))
            }
        }

        state("CatchContact") {
            activators {
                regex("catch (.*)")
            }

            action(regex) {
                val userName = activator.matcher.group(1)
                context.cleanSessionData()

                telegramApi.send(TdApi.SearchContacts(userName, 1)).let { res ->
                    if (res.totalCount == 0) {
                        reactions.say("Sorry, \"$userName\" was not found in your contacts")
                    } else {
                        val user = telegramApi.send(TdApi.GetUser(res.userIds.first()))
                        context.session["user_to_catch"] = user
                        reactions.say("Would you like to catch ${user.firstName} ${user.lastName}?")
                        reactions.buttons("Yes", "No")
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