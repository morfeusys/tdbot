package com.tdbot.server.scenarios

import com.tdbot.scenario.WatchContact
import com.tdbot.server.TdScenarioFactory

data class WatchContactConfig(val contact: String)

val WatchContactFactory =
    TdScenarioFactory.create<WatchContactConfig>("watch_contact.yaml") { config ->
        WatchContact(config.contact, tdBotApi)
    }