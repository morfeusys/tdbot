package com.tdbot.server

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.justai.jaicf.model.scenario.Scenario
import com.tdbot.runtime.ScenariosBuilder
import com.tdbot.server.model.ScenarioDefinition
import java.io.File
import java.io.FileInputStream
import java.nio.file.Paths

sealed class TdScenarioFactory(val filename: String) {

    protected abstract fun ScenariosBuilder.create(definition: ScenarioDefinition) : Scenario

    fun create(builder: ScenariosBuilder, dir: String = "."): Map<String, Scenario> {
        val definitions = mapper
            .readValue(FileInputStream(File(dir, filename)), definitionType)
            .filterValues { it.enabled }
        val scenarios = definitions.mapValues {
            with(builder) { create(it.value) }
        }
        return definitions.mapValues { def ->
            var scenario = scenarios[def.key]!!
            def.value.conditions?.let { conditions ->
                conditions.forEach { condition ->
                    scenario = condition.apply(scenario)
                }
            }
            scenario
        }
    }

    class Simple(
        filename: String,
        private val factory: ScenariosBuilder.() -> Scenario
    ) : TdScenarioFactory(filename) {
        override fun ScenariosBuilder.create(definition: ScenarioDefinition) = factory()
    }

    class Configurable<TConfig>(
        filename: String,
        private val configClass: Class<TConfig>,
        private val factory: ScenariosBuilder.(TConfig) -> Scenario
    ) : TdScenarioFactory(filename) {
        override fun ScenariosBuilder.create(definition: ScenarioDefinition) =
            factory(mapper.convertValue(definition.config, configClass))
    }

    companion object {
        private val definitionType = object : TypeReference<LinkedHashMap<String, ScenarioDefinition>>() {}
        private val mapper = ObjectMapper(YAMLFactory()).registerModule(KotlinModule.Builder().build()).apply {
            enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
            enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
        }

        inline fun <reified TConfig> create(filename: String, noinline factory: ScenariosBuilder.(TConfig) -> Scenario) =
            Configurable(filename, TConfig::class.java, factory)

        fun create(filename: String, factory: ScenariosBuilder.() -> Scenario) =
            Simple(filename, factory)
    }
}