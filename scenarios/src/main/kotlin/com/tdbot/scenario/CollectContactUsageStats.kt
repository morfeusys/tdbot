package com.tdbot.scenario

import com.github.kotlintelegrambot.entities.ParseMode
import com.justai.jaicf.channel.td.api.TdTelegramApi
import com.justai.jaicf.channel.td.scenario.createTdModel
import com.justai.jaicf.channel.td.scenario.onReady
import com.justai.jaicf.channel.td.scenario.onUpdateUserStatus
import com.justai.jaicf.reactions.buttons
import com.justai.jaicf.reactions.toState
import com.tdbot.api.TdInteractiveScenario
import com.tdbot.api.createInteractiveScenario
import com.tdbot.scenario.utils.asStatePath
import it.tdlight.jni.TdApi
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class CollectContactUsageStats(
    private val contactName: String? = null
) : TdInteractiveScenario({
    actionButtons = listOf("Show usage statistics" toState "/ShowContactsUsageStats${contactName.asStatePath}")
}) {
    private lateinit var startTime: LocalDateTime
    private lateinit var telegramApi: TdTelegramApi

    private val stats = mutableMapOf<Long, Stats>()
    private var contactId: Long? = null

    override val model = createTdModel {
        onReady {
            startTime = LocalDateTime.now()
            telegramApi = api
            contactId = contactName?.let {
                val users = api.send(TdApi.SearchContacts(it, 1))
                when (users.totalCount) {
                    0 -> throw IllegalArgumentException("Contact \"$it\" was not found")
                    else -> users.userIds.first()
                }
            }
        }

        onUpdateUserStatus {
            val userId = request.update.userId
            if (contactId == null || contactId == userId) {
                stats.getOrPut(userId) {
                    reactions.api.send(TdApi.GetUser(userId)).let(::Stats)
                }.add(request.update.status)
            }
        }
    }

    override val interactiveScenario = createInteractiveScenario {
        state("/ShowContactsUsageStats${contactName.asStatePath}") {
            action {
                if (contactId != null) {
                    context.session["user_to_show_usage_stat"] = contactId
                    reactions.go("user")
                } else {
                    reactions.say("Send me a contact's name to see their Telegram usage stats")
                    reactions.buttons("Show all" toState "all")
                }
            }

            state("name") {
                activators {
                    catchAll()
                }

                action {
                    val users = telegramApi.send(TdApi.SearchContacts(request.input, 1))
                    if (users.totalCount == 0) {
                        reactions.say("Sorry, I cannot find \"${request.input}\" in your contacts.")
                    } else {
                        context.session["user_to_show_usage_stat"] = users.userIds.first()
                        reactions.go("../user")
                    }
                }
            }

            state("user") {
                action {
                    val userId = context.session["user_to_show_usage_stat"] as Long
                    val stat = stats[userId]
                    if (stat == null || !stat.hasData) {
                        reactions.say("Looks like there is no statistics for this contact yet. Wait a little bit until they become online.")
                    } else {
                        reactions.image(stat.chartImageUrl, "_${stat.user.firstName} ${stat.user.lastName}_ " +
                                "was online *${stat.times} times* " +
                                "starting from ${startTime.format(dateTimeFormatter)} " +
                                "with total *${stat.totalTime}*", ParseMode.MARKDOWN)
                    }
                    reactions.buttons("Refresh" toState ".")
                }
            }

            state("all") {
                action {
                    val users = stats.values
                        .filter { it.hasData && it.user.id != telegramApi.me.id }
                        .sortedBy { it.user.firstName }

                    if (users.isEmpty()) {
                        reactions.say("Looks like there is no any statistics data from your contacts yet. " +
                                "Wait a little bit until they become online.")
                    } else {
                        val chartImageUrl = "https://quickchart.io/chart?w=600&h=600&c={type:'horizontalBar',data:{" +
                                "labels:[${users.map { "'${it.user.firstName} ${it.user.lastName}'" }.joinToString(",")}]," +
                                "datasets:[{label:'Online',data:[${users.map { it.totalSeconds }.joinToString(",")},0]}]}}"

                        reactions.image(chartImageUrl, "Here is an overall usage statistics starting from ${startTime.format(dateTimeFormatter)}")
                        reactions.say("Send me contact's name to see their detailed usage statistics")
                    }
                    reactions.buttons("Refresh" toState ".")
                }
            }
        }
    }

    data class Stats(val user: TdApi.User) {
        private var lastOnline: LocalDateTime? = null
        private val online = mutableMapOf<LocalDateTime, Duration>()

        val chartImageUrl
            get() = "https://quickchart.io/chart?w=600&h=600&c={type:'horizontalBar',data:{" +
                    "labels:[${online.keys.joinToString(",") { "'${it.formattedTime}'" }}]," +
                    "datasets:[{label:'Online',data:[${online.values.map { it.seconds }.joinToString(",")},0]}]}}"

        val times get() = online.size

        val totalSeconds get() = online.values.sumOf { it.seconds }

        val hasData
            get() = times > 0 && totalSeconds > 0

        val totalTime: String get() {
            val seconds = totalSeconds
            val minutes = seconds/60
            return "${minutes/60} hours, ${minutes%60} minutes and ${seconds%60} seconds"
        }

        fun add(status: TdApi.UserStatus) {
            when (status) {
                is TdApi.UserStatusOnline -> lastOnline = LocalDateTime.now()
                is TdApi.UserStatusOffline -> lastOnline?.also { time ->
                    lastOnline = null
                    val key = online.keys.find { it.formattedTime == time.formattedTime } ?: time
                    online[key] = Duration.between(time, LocalDateTime.now()) + (online[key] ?: Duration.ZERO)
                }
            }
        }

        private val LocalDateTime.formattedTime
            get() = format(timeFormatter)
    }

    companion object {
        private val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        private val dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm MMM d", Locale.US)
    }
}