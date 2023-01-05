package com.tdbot.scenario

import com.google.gson.JsonArray
import com.justai.jaicf.channel.td.isOutgoing
import com.justai.jaicf.channel.td.scenario.TdScenario
import com.justai.jaicf.channel.td.scenario.onTextMessage
import com.tdbot.scenario.utils.Http
import io.ktor.client.request.*
import kotlin.math.abs

val FixMySpellWithYandexSpeller = TdScenario {

    onTextMessage(isOutgoing) {
        var text = request.input
        val res = Http.get<JsonArray>("https://speller.yandex.net/services/spellservice.json/checkText") {
            parameter("text", text)
        }
        var index = 0
        res.map { it.asJsonObject }
            .forEach { e ->
                val pos = index + e["pos"].asInt
                val len = e["len"].asInt
                val replacement = e.getAsJsonArray("s").first().asString
                index += abs(replacement.length - len)
                text = text.replaceRange(pos, pos + len, replacement)
            }

        if (text != request.input) {
            reactions.editText(text)
        }
    }
}