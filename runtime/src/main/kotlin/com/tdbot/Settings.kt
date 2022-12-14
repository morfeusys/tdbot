package com.tdbot

import com.tdbot.runtime.TdRuntime
import java.util.*

private val props = Properties().apply {  load(ClassLoader.getSystemResourceAsStream("tdbot.properties")) }

val defaultRuntimeSettings = TdRuntime.Settings(
    apiId = (System.getenv("TD_API_ID") ?: props.getProperty("api_id")).toInt(),
    apiHash = System.getenv("TD_API_HASH") ?: props.getProperty("api_hash"),
    tdBotToken = System.getenv("TD_BOT_TOKEN") ?: props.getProperty("bot_token"),
    tdDirectory = System.getenv("TD_DIRECTORY") ?: props.getProperty("td_directory") ?: ".td",
    publicQrLink = System.getenv("TD_PUBLIC_QR_LINK") ?: "https://tdbot.vercel.app/"
)