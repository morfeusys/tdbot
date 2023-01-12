package com.justai.jaicf.channel.td.request

import com.justai.jaicf.channel.invocationapi.InvocationEventRequest
import com.justai.jaicf.channel.invocationapi.InvocationQueryRequest
import it.tdlight.jni.TdApi
import java.io.DataOutput

data class TdEventUpdate(
    val chatId: Long,
    val event: String,
    val data: String,
) : TdApi.Update() {
    override fun getConstructor() = 0
    override fun serialize(p0: DataOutput?) {}
}

val InvocationEventRequest.asEventUpdate
    get() = TdEventUpdate(
        clientId.toLong(),
        input,
        requestData
    )

val InvocationQueryRequest.asNewMessageUpdate
    get() = TdApi.UpdateNewMessage(TdApi.Message(
        System.currentTimeMillis(),
        TdApi.MessageSenderUser(clientId.toLong()),
        clientId.toLong(),
        null,
        null,
        false,
        false,
        true,
        true,
        true,
        false,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        true,
        false,
        false,
        false,
        (System.currentTimeMillis() / 1000).toInt(),
        0,
        null,
        null,
        null,
        0L,
        0L,
        0L,
        0,
        .0,
        .0,
        0,
        "",
        0L,
        "",
        TdApi.MessageText(TdApi.FormattedText(input, null),null),
        null
    ))