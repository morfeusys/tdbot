package com.tdbot.server.scenarios

import com.tdbot.scenario.FixMySpellWithYandexSpeller
import com.tdbot.server.TdScenarioFactory

val YandexSpeller = TdScenarioFactory.create("yandex_speller.yaml") { FixMySpellWithYandexSpeller }