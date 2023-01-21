package com.justai.gramlin.server.scenarios

import com.justai.gramlin.scenario.CollectContactUsageStats
import com.justai.gramlin.server.ScenarioFactory

data class ContactUsageStatsConfig(
    val contact: String? = null
)

val ContactUsageStats =
    ScenarioFactory.create<ContactUsageStatsConfig>("contact_usage_stats.yaml") { config ->
        CollectContactUsageStats(config.contact)
    }