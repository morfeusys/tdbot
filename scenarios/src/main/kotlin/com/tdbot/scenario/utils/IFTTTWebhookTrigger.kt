package com.tdbot.scenario.utils

class IFTTTWebhookTrigger(private val key: String) {
    fun sendPayload(event: String, value1: Any? = null, value2: Any? = null, value3: Any? = null) {
        Http.post("https://maker.ifttt.com/trigger/$event/json/with/key/$key") {
            body = mapOf(
                "value1" to value1,
                "value2" to value2,
                "value3" to value3
            )
        }
    }
}