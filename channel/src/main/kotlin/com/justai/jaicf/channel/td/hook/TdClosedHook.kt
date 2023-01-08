package com.justai.jaicf.channel.td.hook

import com.justai.jaicf.channel.td.client.TdTelegramApi
import com.justai.jaicf.context.BotContext
import com.justai.jaicf.context.DialogContext
import com.justai.jaicf.helpers.logging.WithLogger
import com.justai.jaicf.hook.BotHook

data class TdClosedHook(
    val api: TdTelegramApi
): BotHook, WithLogger {
    val user = api.me
    override val context = BotContext(user.id.toString(), DialogContext())
}
