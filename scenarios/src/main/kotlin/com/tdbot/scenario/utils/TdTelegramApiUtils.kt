package com.tdbot.scenario.utils

import com.justai.jaicf.channel.td.client.TdTelegramApi
import it.tdlight.client.GenericResultHandler
import it.tdlight.jni.TdApi

fun TdTelegramApi.searchChats(query: String, limit: Int = 1, resultHandler: GenericResultHandler<TdApi.Chats>) =
    send(TdApi.SearchChats(query, limit)) { res1 ->
        if (res1.isError || res1.get().totalCount == 0) {
            send(TdApi.SearchChatsOnServer(query, limit), resultHandler)
        } else {
            resultHandler.onResult(res1)
        }
    }