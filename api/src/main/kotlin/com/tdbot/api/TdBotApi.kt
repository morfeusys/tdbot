package com.tdbot.api

interface TdBotApi {
    fun sendEvent(event: String, data: String = "")
    fun sendMessage(message: String)
}