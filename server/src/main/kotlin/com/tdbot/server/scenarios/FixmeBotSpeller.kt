package com.tdbot.server.scenarios

import com.tdbot.scenario.FixMySpellWithFixmeBot
import com.tdbot.server.TdScenarioFactory

val FixmeBotSpeller = TdScenarioFactory.create("fixmebot_speller.yaml") { FixMySpellWithFixmeBot }