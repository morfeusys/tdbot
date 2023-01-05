package com.tdbot.scenario.utils

val String.multilineCaseInsensitiveRegex
    get() = Regex(this, setOf(RegexOption.IGNORE_CASE, RegexOption.MULTILINE))