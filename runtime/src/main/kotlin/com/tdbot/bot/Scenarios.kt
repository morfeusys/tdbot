package com.tdbot.bot

import com.tdbot.api.TdInteractiveScenario
import com.justai.jaicf.model.scenario.Scenario

class Scenarios(
    val all: Map<String, Scenario>
) {
    private val _disabled = mutableSetOf<String>()

    val disabled get() = all.filterKeys(_disabled::contains)

    val enabled get() = all.filterKeys { !disabled.containsKey(it) }

    val interactive get() = all.values.filterIsInstance<TdInteractiveScenario>()

    fun toggle(scenario: String) = when {
        isEnabled(scenario) -> disable(scenario)
        else -> enable(scenario)
    }.let { isEnabled(scenario) }

    fun enable(scenario: String) = _disabled.remove(scenario)

    fun disable(scenario: String) = _disabled.add(scenario)

    fun isEnabled(scenario: String) = !_disabled.contains(scenario)

    fun isDisabled(scenario: String) = _disabled.contains(scenario)

    fun isInteractive(scenario: String) = all[scenario] is TdInteractiveScenario
}