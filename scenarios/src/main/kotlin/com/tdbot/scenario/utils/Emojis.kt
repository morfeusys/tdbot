package com.tdbot.scenario.utils

import com.vdurmont.emoji.EmojiManager
import it.tdlight.jni.TdApi

val String.asEmoji
    get() = EmojiManager.getForAlias(this)

val String.asEmojiPattern
    get() = asEmoji.unicode

val String.asEmojiReaction
    get() = TdApi.ReactionTypeEmoji(asEmoji.unicode)

fun TdApi.MessageSticker.isEmoji(emojiAlias: String) =
    sticker.emoji == emojiAlias.asEmoji.unicode

fun TdApi.MessageAnimatedEmoji.isEmoji(emojiAlias: String) =
    emoji == emojiAlias.asEmoji.unicode