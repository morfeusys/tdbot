package com.tdbot.scenario

import com.justai.jaicf.channel.td.TdReactions
import com.justai.jaicf.channel.td.TdRequest
import com.justai.jaicf.channel.td.isClient
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onReady
import com.justai.jaicf.channel.td.scenario.onUpdateUserStatus
import com.justai.jaicf.context.ActionContext
import com.justai.jaicf.context.ActivatorContext
import it.tdlight.jni.TdApi

fun SpyContact(
    phoneNumber: String,
    handler: ActionContext<ActivatorContext, TdRequest<TdApi.UpdateUserStatus>, TdReactions>.(user: TdApi.User) -> Unit,
) = TdScenario {
    var user: TdApi.User? = null

    onReady {
        user = api.send(TdApi.SearchUserByPhoneNumber(phoneNumber))
    }

    onUpdateUserStatus(isClient {user?.id?.toString()}) {
        handler(this, user!!)
    }
}