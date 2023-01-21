package com.justai.gramlin.server.scenarios

import com.justai.gramlin.scenario.FixMySpellWithFixmeBot
import com.justai.gramlin.server.ScenarioFactory

val FixmeBotSpeller = ScenarioFactory.create("fixmebot_speller.yaml") { FixMySpellWithFixmeBot }