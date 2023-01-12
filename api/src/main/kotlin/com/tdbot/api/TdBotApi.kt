package com.tdbot.api

import com.justai.jaicf.channel.td.api.TdTelegramApi

interface TdBotApi {
    val chatId: Long?
    val telegram: TdTelegramApi

    private fun <R> withChatId(function: (chatId: Long) -> R): R? = chatId?.let(function)

    fun sendEvent(event: String, data: String = "")
}