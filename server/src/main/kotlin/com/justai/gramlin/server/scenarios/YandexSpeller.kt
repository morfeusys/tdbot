package com.justai.gramlin.server.scenarios

import com.justai.gramlin.scenario.FixMySpellWithYandexSpeller
import com.justai.gramlin.server.ScenarioFactory

val YandexSpeller = ScenarioFactory.create("yandex_speller.yaml") { FixMySpellWithYandexSpeller }