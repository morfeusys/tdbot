package com.justai.gramlin.server.scenarios

import com.justai.gramlin.scenario.CatchContact
import com.justai.gramlin.server.ScenarioFactory

val CatchContactFactory = ScenarioFactory.create("catch_contact.yaml") { CatchContact }