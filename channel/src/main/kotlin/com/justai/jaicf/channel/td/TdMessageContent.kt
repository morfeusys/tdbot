package com.justai.jaicf.channel.td

import it.tdlight.jni.TdApi

val TdApi.MessageContent.text: TdApi.FormattedText?
    get() = when (this) {
        is TdApi.MessageText -> text
        is TdApi.MessagePhoto -> caption
        is TdApi.MessageAnimation -> caption
        is TdApi.MessageVideo -> caption
        is TdApi.MessageVoiceNote -> caption
        is TdApi.MessageAudio -> caption
        else -> null
    }