package com.tdbot.server.scenarios

import com.tdbot.scenario.CatchContact
import com.tdbot.server.TdScenarioFactory

val CatchContactFactory = TdScenarioFactory.create("catch_contact.yaml") { CatchContact }