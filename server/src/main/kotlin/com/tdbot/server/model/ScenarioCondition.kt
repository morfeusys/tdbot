package com.tdbot.server.model

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.justai.jaicf.model.scenario.Scenario
import com.tdbot.api.*

@JsonTypeInfo(use = JsonTypeInfo.Id.DEDUCTION)
sealed interface ScenarioCondition {

    fun apply(scenario: Scenario): Scenario

    data class Status(val status: Status) : ScenarioCondition {
        enum class Status { ONLINE, OFFLINE }

        override fun apply(scenario: Scenario) = when (status) {
            Status.ONLINE -> scenario.onlyOnline
            Status.OFFLINE -> scenario.onlyOffline
        }
    }

    data class Outgoing(val outgoing: Boolean) : ScenarioCondition {
        override fun apply(scenario: Scenario) = when (outgoing) {
            true -> scenario.onlyOutgoing
            else -> scenario.onlyIncoming
        }
    }

    data class ChannelPosts(val channelPosts: Boolean) : ScenarioCondition {
        override fun apply(scenario: Scenario) = when (channelPosts) {
            true -> scenario.onlyChannelPosts
            else -> scenario.onlyNonChannelPosts
        }
    }

    data class Chats(val chats: List<String>) : ScenarioCondition {
        override fun apply(scenario: Scenario) =
            scenario.onlyInChats(*chats.toTypedArray())
    }

    data class Chat(val chat: String) : ScenarioCondition {
        override fun apply(scenario: Scenario) =
            scenario.onlyInChats(chat)
    }

    data class NotChats(val notChats: List<String>) : ScenarioCondition {
        override fun apply(scenario: Scenario) =
            scenario.onlyNotInChats(*notChats.toTypedArray())
    }

    data class NotChat(val notChat: String) : ScenarioCondition {
        override fun apply(scenario: Scenario) =
            scenario.onlyNotInChats(notChat)
    }

    data class Contacts(val contacts: List<String>) : ScenarioCondition {
        override fun apply(scenario: Scenario) =
            scenario.onlyWithContacts(*contacts.toTypedArray())
    }

    data class Contact(val contact: String) : ScenarioCondition {
        override fun apply(scenario: Scenario) =
            scenario.onlyWithContacts(contact)
    }

    data class NotContacts(val notContacts: List<String>) : ScenarioCondition {
        override fun apply(scenario: Scenario) =
            scenario.onlyWithNotContacts(*notContacts.toTypedArray())
    }

    data class NotContact(val notContact: String) : ScenarioCondition {
        override fun apply(scenario: Scenario) =
            scenario.onlyWithNotContacts(notContact)
    }
}