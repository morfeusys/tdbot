package com.justai.gramlin.server.scenarios

import com.justai.gramlin.api.TdMessageContentType
import com.justai.gramlin.scenario.ReactOnMessage
import com.justai.gramlin.scenario.utils.Http
import com.justai.gramlin.server.ScenarioFactory
import com.justai.gramlin.server.executeTemplate
import io.ktor.client.request.*
import io.ktor.client.utils.*
import io.ktor.http.*

data class SendHttpOnMessageConfig(
    val message: Message,
    val http: Http
) {
    data class Message(
        val pattern: String,
        val types: List<TdMessageContentType> = emptyList()
    )

    data class Http(
        val url: String,
        val method: String = "get",
        val headers: Map<String, String> = emptyMap(),
        val params: Map<String, String> = emptyMap(),
        val body: String? = null,
    )
}

val SendHttpOnMessage =
    ScenarioFactory.create<SendHttpOnMessageConfig>("send_http_on_message.yaml") { config ->
        ReactOnMessage(config.message.pattern.executeTemplate(), config.message.types.toTypedArray()) {
            Http.request(config.http.url.executeTemplate(request)) {
                method = HttpMethod.parse(config.http.method.uppercase())
                body = config.http.body?.executeTemplate(request) ?: EmptyContent
                config.http.headers.forEach { (key, value) ->
                    header(key.executeTemplate(request), value.executeTemplate(request))
                }
                config.http.params.forEach { (key, value) ->
                    parameter(key.executeTemplate(request), value.executeTemplate(request))
                }
            }
        }
    }