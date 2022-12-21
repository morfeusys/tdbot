package com.github.kotlintelegrambot.dispatcher.handlers

import com.github.kotlintelegrambot.Bot
import com.github.kotlintelegrambot.entities.Message
import com.github.kotlintelegrambot.entities.Update
import com.github.kotlintelegrambot.entities.WebAppData

data class WebAppDataHandlerEnvironment(
    val bot: Bot,
    val update: Update,
    val message: Message,
    val data: WebAppData
)

internal class WebAppDataHandler(
    private val handleWebAppData: HandleWebAppData
) : Handler {
    override fun checkUpdate(update: Update) =
        update.message?.webAppData != null

    override fun handleUpdate(bot: Bot, update: Update) {
        checkNotNull(update.message)
        checkNotNull(update.message.webAppData)
        val webAppDataHandlerEnv = WebAppDataHandlerEnvironment(
            bot,
            update,
            update.message,
            update.message.webAppData
        )
        handleWebAppData(webAppDataHandlerEnv)
    }
}