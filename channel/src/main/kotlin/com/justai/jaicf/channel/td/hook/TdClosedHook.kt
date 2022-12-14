package com.justai.jaicf.channel.td.hook

import com.justai.jaicf.context.BotContext
import com.justai.jaicf.context.DialogContext
import com.justai.jaicf.hook.BotHook
import it.tdlight.client.SimpleTelegramClient
import it.tdlight.jni.TdApi

data class TdClosedHook(
    val api: SimpleTelegramClient,
    val user: TdApi.User,
): BotHook {
    override val context = BotContext(user.id.toString(), DialogContext())
}
