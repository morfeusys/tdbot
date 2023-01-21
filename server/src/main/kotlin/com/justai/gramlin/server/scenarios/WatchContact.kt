package com.justai.gramlin.server.scenarios

import com.justai.gramlin.scenario.WatchContact
import com.justai.gramlin.server.ScenarioFactory

data class WatchContactConfig(val contact: String)

val WatchContactFactory =
    ScenarioFactory.create<WatchContactConfig>("watch_contact.yaml") { config ->
        WatchContact(config.contact, gramlinBot)
    }