package com.justai.gramlin

import com.justai.gramlin.runtime.GramlinRuntime
import java.util.*

private val props = Properties().apply {  load(ClassLoader.getSystemResourceAsStream("gramlin.properties")) }

val defaultRuntimeSettings = GramlinRuntime.Settings(
    apiId = (System.getenv("TD_API_ID") ?: props.getProperty("api_id")).toInt(),
    apiHash = System.getenv("TD_API_HASH") ?: props.getProperty("api_hash"),
    tdBotToken = System.getenv("TD_BOT_TOKEN") ?: props.getProperty("bot_token"),
    tdDirectory = System.getenv("TD_DIRECTORY") ?: props.getProperty("td_directory") ?: ".td"
)