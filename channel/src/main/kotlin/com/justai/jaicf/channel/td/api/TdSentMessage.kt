package com.justai.jaicf.channel.td.api

import it.tdlight.jni.TdApi
import java.util.concurrent.CompletableFuture

internal data class TdSentMessage(
    val message: TdApi.Message
) : CompletableFuture<TdApi.Message>() {
    val id = message.id
    val date = message.date
}