package com.justai.gramlin.server

import com.justai.gramlin.server.scenarios.*

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