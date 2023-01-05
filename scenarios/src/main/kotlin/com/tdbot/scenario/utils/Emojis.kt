package com.tdbot.scenario.utils

import com.vdurmont.emoji.EmojiManager
import com.vdurmont.emoji.EmojiParser
import it.tdlight.jni.TdApi

/**
 * See all available emoji aliases on https://github.com/vdurmont/emoji-java/blob/master/EMOJIS.md
 */

val String.withoutEmojis
    get() = EmojiParser.removeAllEmojis(this)

val String.asEmoji
    get() = EmojiManager.getForAlias(this)

val String.asEmojiPattern
    get() = asEmojiUnicode

val String.asEmojiUnicode
    get() = asEmoji.unicode

val String.asEmojiReaction
    get() = TdApi.ReactionTypeEmoji(asEmojiUnicode)

fun TdApi.MessageSticker.isEmoji(emojiAlias: String) =
    sticker.emoji == emojiAlias.asEmojiUnicode

fun TdApi.MessageAnimatedEmoji.isEmoji(emojiAlias: String) =
    emoji == emojiAlias.asEmojiUnicode