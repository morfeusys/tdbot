package com.justai.jaicf.channel.td.hook

import com.justai.jaicf.channel.td.client.TdTelegramApi
import com.justai.jaicf.context.BotContext
import com.justai.jaicf.context.DialogContext
import com.justai.jaicf.hook.BotHook
import it.tdlight.jni.TdApi

data class TdReadyHook(
    val api: TdTelegramApi,
    val user: TdApi.User,
): BotHook {
    override val context = BotContext(user.id.toString(), DialogContext())
}