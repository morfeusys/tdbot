package com.justai.jaicf.channel.td.client

import it.tdlight.jni.TdApi

fun TdTelegramApi.searchChats(query: String, limit: Int = 1): TdApi.Chats {
    var chats = send(TdApi.SearchChats(query, limit))
    if (chats.totalCount == 0) {
        chats = send(TdApi.SearchChatsOnServer(query, limit))
    }
    return chats
}

fun TdTelegramApi.searchPublicChats(query: String, limit: Int = 1): TdApi.Chats {
    var chats = searchChats(query, limit)
    if (chats.totalCount == 0) {
        chats = send(TdApi.SearchPublicChats(query))
    }
    return chats
}