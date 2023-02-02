package com.justai.gramlin.scenario.utils

import com.vdurmont.emoji.EmojiManager
import com.vdurmont.emoji.EmojiParser
import it.tdlight.jni.TdApi

val String?.asStatePath
    get() = takeIf { this != null }?.let { "/$it" } ?: ""

val String.asInlineButtonTitle
    get() = take(21).let { text ->
        when {
            text.length < length -> text.replaceRange(18..20, "...")
            else -> text
        }
    }

val String.withoutEmojis
    get() = EmojiParser.removeAllEmojis(this)

val String.asEmojiReaction
    get() = TdApi.ReactionTypeEmoji(this)

val String.asEmoji
    get() = EmojiManager.getForAlias(this)

val String.asEmojiUnicode
    get() = asEmoji.unicode