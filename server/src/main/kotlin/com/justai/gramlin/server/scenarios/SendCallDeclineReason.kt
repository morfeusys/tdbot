package com.justai.gramlin.server.scenarios

import com.justai.gramlin.scenario.SendReasonOnCallDecline
import com.justai.gramlin.server.ScenarioFactory
import com.justai.gramlin.server.executeTemplate

data class SendCallDeclineReasonConfig(
    val reasons: List<String>
)

val SendCallDeclineReason =
    ScenarioFactory.create<SendCallDeclineReasonConfig>("send_call_decline_reason.yaml") { config ->
        SendReasonOnCallDecline(
            *config.reasons.map { it.executeTemplate() }.toTypedArray()
        )
    }