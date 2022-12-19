package com.justai.jaicf.channel.td.client

import it.tdlight.client.GenericResultHandler
import it.tdlight.client.SimpleTelegramClient
import it.tdlight.jni.TdApi
import org.slf4j.LoggerFactory
import java.util.concurrent.Executors

class TdTelegramApi(val client: SimpleTelegramClient) {
    private val logger = LoggerFactory.getLogger(javaClass)
    private val singleThreadExecutor = Executors.newSingleThreadExecutor()

    fun <R : TdApi.Object> send(function: TdApi.Function<R>, resultHandler: GenericResultHandler<R>) {
        logger.info("Sending $function")
        singleThreadExecutor.execute {
            client.send(function) { res ->
                logger.info("Received result $res")
                singleThreadExecutor.execute { resultHandler.onResult(res) }
            }
        }
    }
}