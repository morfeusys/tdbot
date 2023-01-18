package com.tdbot.server

import com.tdbot.server.scenarios.*

val scenarioFactories = listOf(
    ReplyOnMessage,
    ReplyFromChat,
    ReplaceText,
    ForwardMessage,
    BilyBotUrlShortener,
    TranscriberBotVoiceTranscriber,
    YandexSpeller,
    FixmeBotSpeller,
    WatchContactFactory,
    CatchContactFactory,
    ContactUsageStats,
    SendCallDeclineReason,
    SendHttpOnMessage,
).sortedBy { it.filename }