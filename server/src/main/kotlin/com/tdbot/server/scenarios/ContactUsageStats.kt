package com.tdbot.server.scenarios

import com.tdbot.scenario.CollectContactUsageStats
import com.tdbot.server.TdScenarioFactory

data class ContactUsageStatsConfig(
    val contact: String? = null
)

val ContactUsageStats =
    TdScenarioFactory.create<ContactUsageStatsConfig>("contact_usage_stats.yaml") { config ->
        CollectContactUsageStats(config.contact)
    }