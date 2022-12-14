package com.tdbot.scenario

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.request.*
import io.ktor.client.statement.*

data class IFTTTPayload(
    val value1: Any?,
    val value2: Any?,
    val value3: Any?,
)

class IFTTTWebhookTrigger(private val key: String) {
    suspend fun sendPayload(event: String, value1: Any? = null, value2: Any? = null, value3: Any? = null) {
        client.post<HttpResponse>("https://maker.ifttt.com/trigger/${event}/json/with/key/${key}") {
            body = IFTTTPayload(value1, value2, value3)
        }
    }

    companion object {
        private val client = HttpClient(CIO) {
            install(JsonFeature) {
                serializer = GsonSerializer()
            }
        }
    }
}
