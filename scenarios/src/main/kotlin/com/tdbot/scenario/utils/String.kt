package com.tdbot.scenario.utils

val String?.asStatePath
    get() = takeIf { this != null }?.let { "/$it" } ?: ""