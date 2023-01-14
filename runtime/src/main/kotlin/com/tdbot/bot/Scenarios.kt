package com.tdbot.bot

import com.tdbot.api.TdInteractiveScenario
import com.justai.jaicf.model.scenario.Scenario
import java.nio.file.Path
import java.nio.file.Paths

class Scenarios(
    val all: Map<String, Scenario>,
    storagePath: String,
) {
    private val _disabled = mutableSetOf<String>()
    private val storage = Paths.get(storagePath, "disabled.scenarios").toFile()

    init {
        if (storage.exists()) {
            _disabled.addAll(storage.readLines().filter(all::containsKey))
        }
    }

    private fun store() {
        storage.writeText(_disabled.joinToString("\n"))
    }

    val disabled get() = all.filterKeys(_disabled::contains)

    val enabled get() = all.filterKeys { !disabled.containsKey(it) }

    val interactive get() = all.values.filterIsInstance<TdInteractiveScenario>()

    fun toggle(scenario: String) = when {
        isEnabled(scenario) -> disable(scenario)
        else -> enable(scenario)
    }.let { isEnabled(scenario) }

    fun enable(scenario: String) = _disabled.remove(scenario).also { store() }

    fun disable(scenario: String) = _disabled.add(scenario).also { store() }

    fun isEnabled(scenario: String) = !_disabled.contains(scenario)

    fun isDisabled(scenario: String) = _disabled.contains(scenario)

    fun isInteractive(scenario: String) = all[scenario] is TdInteractiveScenario

    fun findScenarioName(query: String) = all.keys.find { it.equals(query, true) }
}