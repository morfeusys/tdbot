package com.tdbot.server.model

import com.fasterxml.jackson.databind.JsonNode

data class ScenarioDefinition(
    val enabled: Boolean,
    val config: JsonNode?,
    val conditions: List<ScenarioCondition>?,
)
