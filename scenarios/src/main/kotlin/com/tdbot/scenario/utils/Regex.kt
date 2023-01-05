package com.tdbot.scenario.utils

val String.multilineCaseInsensitiveRegex
    get() = this.toRegex(setOf(RegexOption.IGNORE_CASE, RegexOption.MULTILINE))