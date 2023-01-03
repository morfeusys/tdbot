package com.tdbot.scenario

import com.justai.jaicf.channel.td.TdActionContext
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onReady
import com.justai.jaicf.channel.td.scenario.onUpdateUserStatus
import it.tdlight.jni.TdApi

fun SpyContact(
    phoneNumber: String,
    handler: TdActionContext.(user: TdApi.User, status: TdApi.UpdateUserStatus) -> Unit,
) = TdScenario {
    var user: TdApi.User? = null

    onReady {
        user = api.send(TdApi.SearchUserByPhoneNumber(phoneNumber))
    }

    onUpdateUserStatus {
        if (request.update.userId == user?.id) {
            handler(this, user!!, request.update)
        }
    }
}