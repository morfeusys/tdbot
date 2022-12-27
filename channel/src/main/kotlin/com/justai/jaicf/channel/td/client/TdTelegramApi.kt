package com.justai.jaicf.channel.td.client

import it.tdlight.client.GenericResultHandler
import it.tdlight.client.SimpleTelegramClient
import it.tdlight.client.TelegramError
import it.tdlight.jni.TdApi
import java.util.concurrent.Executors
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class TdTelegramApi(val client: SimpleTelegramClient) {
    private val singleThreadExecutor = Executors.newSingleThreadExecutor()

    fun <R : TdApi.Object> send(function: TdApi.Function<R>, resultHandler: GenericResultHandler<R>) {
        singleThreadExecutor.execute {
            client.send(function, resultHandler)
        }
    }

    suspend fun <R : TdApi.Object> send(function: TdApi.Function<R>) =
        suspendCoroutine<R> { continuation ->
            send(function) { res ->
                if (res.isError) {
                    continuation.resumeWithException(TelegramError(res.error))
                } else {
                    continuation.resume(res.get())
                }
            }
        }
}