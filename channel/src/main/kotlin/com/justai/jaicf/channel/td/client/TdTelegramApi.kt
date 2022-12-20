package com.justai.jaicf.channel.td.client

import it.tdlight.client.GenericResultHandler
import it.tdlight.client.SimpleTelegramClient
import it.tdlight.jni.TdApi
import java.util.concurrent.Executors

class TdTelegramApi(val client: SimpleTelegramClient) {
    private val singleThreadExecutor = Executors.newSingleThreadExecutor()

    fun <R : TdApi.Object> send(function: TdApi.Function<R>, resultHandler: GenericResultHandler<R>) {
        singleThreadExecutor.execute {
            client.send(function, resultHandler)
        }
    }
}