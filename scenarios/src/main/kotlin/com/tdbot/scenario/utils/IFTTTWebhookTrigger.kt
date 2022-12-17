package com.tdbot.scenario.utils

data class IFTTTPayload(
    val value1: Any?,
    val value2: Any?,
    val value3: Any?,
)

class IFTTTWebhookTrigger(private val key: String) {
    fun sendPayload(event: String, value1: Any? = null, value2: Any? = null, value3: Any? = null) {
        Http.post("https://maker.ifttt.com/trigger/${event}/json/with/key/${key}") {
            body = IFTTTPayload(value1, value2, value3)
        }
    }
}
