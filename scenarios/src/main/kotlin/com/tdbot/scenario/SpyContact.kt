package com.tdbot.scenario

import com.justai.jaicf.channel.td.TdActionContext
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onReady
import com.justai.jaicf.channel.td.scenario.onUpdateUserStatus
import it.tdlight.jni.TdApi
import org.slf4j.LoggerFactory

fun SpyContact(
    phoneNumber: String,
    handler: TdActionContext.(user: TdApi.User, status: TdApi.UpdateUserStatus) -> Unit,
) = TdScenario {
    val logger = LoggerFactory.getLogger("SpyContact")
    var user: TdApi.User? = null

    onReady {
        api.send(TdApi.SearchUserByPhoneNumber(phoneNumber)) { res ->
            if (res.isError) {
                logger.error("Cannot find user $phoneNumber", res.error.message)
            } else {
                user = res.get()
            }
        }
    }

    onUpdateUserStatus {
        if (request.update.userId == user?.id) {
            handler(this, user!!, request.update)
        }
    }
}