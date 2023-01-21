package com.justai.gramlin.runtime

import com.justai.jaicf.channel.td.api.TdBotApi
import com.justai.jaicf.model.scenario.Scenario
import com.justai.gramlin.api.GramlinInteractiveScenario
import com.justai.gramlin.bot.Scenarios

class ScenariosBuilder(
    val settings: GramlinRuntime.Settings,
    val gramlinBot: TdBotApi
) {
    private val map = mutableMapOf<String, Scenario>()

    infix fun String.to(scenario: Scenario) = requireAlphanumeric.apply {
        if (map.containsKey(this)) {
            throw IllegalArgumentException("There is another scenario exists with name \"$this\"")
        } else if (map.containsValue(scenario)) {
            throw IllegalArgumentException("Scenario instance with name \"$this\" is already exists")
        } else {
            map[this] = scenario
            if (scenario is GramlinInteractiveScenario) {
                scenario.gramlinBot = gramlinBot
            }
        }
    }

    internal fun build() = Scenarios(map.toMap(), settings.tdDirectory)

    companion object {
        private val alphanumeric = Regex("[a-zA-Z0-9]+")
        private val String.requireAlphanumeric get() = apply {
            require(matches(alphanumeric)) {
                "Wrong scenario name \"$this\". Scenario name must contain only latin letters and numbers without spaces and special symbols."
            }
        }
    }
}