package com.tdbot

import com.tdbot.runtime.TdRuntime

val defaultRuntimeSettings = TdRuntime.Settings(
    apiId = System.getenv("TD_API_ID").toInt(),
    apiHash = System.getenv("TD_API_HASH"),
    tdBotToken = System.getenv("TD_BOT_TOKEN"),
    tdDirectory = System.getenv("TD_DIRECTORY") ?: ".td",
    publicQrLink = System.getenv("TD_PUBLIC_QR_LINK") ?: "http://127.0.0.1:8080"
)