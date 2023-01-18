package com.tdbot.server.scenarios

import com.tdbot.scenario.SendReasonOnCallDecline
import com.tdbot.server.TdScenarioFactory
import com.tdbot.server.executeTemplate

data class SendCallDeclineReasonConfig(
    val reasons: List<String>
)

val SendCallDeclineReason =
    TdScenarioFactory.create<SendCallDeclineReasonConfig>("send_call_decline_reason.yaml") { config ->
        SendReasonOnCallDecline(
            *config.reasons.map { it.executeTemplate() }.toTypedArray()
        )
    }