package com.tdbot.server.scenarios

import com.tdbot.scenario.ShortenMyUrlsWithBitlyBot
import com.tdbot.server.TdScenarioFactory

data class BilyBotUrlShortenerConfig(val maxLength: Int)

val BilyBotUrlShortener =
    TdScenarioFactory.create<BilyBotUrlShortenerConfig>("bitlybot_url_shortener.yaml") { config ->
        ShortenMyUrlsWithBitlyBot(config.maxLength)
    }