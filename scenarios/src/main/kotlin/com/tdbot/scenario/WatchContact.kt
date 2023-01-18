package com.tdbot.scenario

import com.justai.jaicf.channel.td.TdMessage
import com.justai.jaicf.channel.td.api.TdBotApi
import com.justai.jaicf.channel.td.isFromId
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onReady
import com.justai.jaicf.channel.td.scenario.onUpdateUserStatus
import com.tdbot.scenario.utils.emoji_red_circle
import it.tdlight.jni.TdApi

fun WatchContact(contact: String, tdBotApi: TdBotApi) = TdScenario {
    var user: TdApi.User? = null

    onReady {
        api.send(TdApi.SearchContacts(contact, 1)).userIds.firstOrNull()?.also { userId ->
            user = api.send(TdApi.GetUser(userId))
        }
    }

    onUpdateUserStatus(isFromId { user?.id }) {
        if (request.update.status is TdApi.UserStatusOnline) {
            tdBotApi.sendText("$emoji_red_circle *${user!!.firstName} ${user!!.lastName}* is online", TdMessage.ParseMode.Markdown)
        }
    }
}