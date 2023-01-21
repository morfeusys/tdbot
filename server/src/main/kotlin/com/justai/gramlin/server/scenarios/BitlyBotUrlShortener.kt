package com.justai.gramlin.server.scenarios

import com.justai.gramlin.scenario.ShortenMyUrlsWithBitlyBot
import com.justai.gramlin.server.ScenarioFactory

data class BilyBotUrlShortenerConfig(val maxLength: Int)

val BilyBotUrlShortener =
    ScenarioFactory.create<BilyBotUrlShortenerConfig>("bitlybot_url_shortener.yaml") { config ->
        ShortenMyUrlsWithBitlyBot(config.maxLength)
    }