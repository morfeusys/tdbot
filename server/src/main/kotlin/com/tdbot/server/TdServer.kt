package com.tdbot.server

import com.diogonunes.jcolor.Ansi
import com.diogonunes.jcolor.Attribute
import com.tdbot.runtime.TdRuntime
import java.io.FileInputStream
import java.nio.file.FileSystems
import java.nio.file.Paths
import java.util.*
import kotlin.io.path.absolutePathString
import kotlin.system.exitProcess

fun main() {
    System.setProperty("logback.configurationFile", resolvePath("logback.xml"))

    val props = Properties().apply { load(FileInputStream(resolvePath("tdbot.properties"))) }
    propKeys.forEach { (key, message) ->
        if (!props.containsKey(key) || props.getProperty(key).isBlank()) {
            println(Ansi.colorize("\nCannot start tdBot\n".uppercase(), Attribute.BOLD(), Attribute.RED_TEXT()))
            println(Ansi.colorize(message, Attribute.YELLOW_TEXT()))
            exitProcess(1)
        }
    }

    val settings = TdRuntime.Settings(
        apiId = props.getProperty("api_id").toInt(),
        apiHash = props.getProperty("api_hash"),
        tdBotToken = props.getProperty("bot_token"),
        tdDirectory = resolvePath(".td")
    )

    println(Ansi.colorize("\ntdBot is starting\n".uppercase(), Attribute.BOLD(), Attribute.GREEN_TEXT()))

    TdRuntime(settings).start {
        scenarioFactories.forEach { factory ->
            factory.create(this, resolvePath("scenarios"))
                .takeIf { it.isNotEmpty() }
                ?.also { println(Ansi.colorize("Loading ${it.size} scenario(s) from ${factory.filename}", Attribute.ITALIC())) }
                ?.forEach { (name, scenario) ->
                    print(Ansi.colorize(name, Attribute.BOLD(), Attribute.BLUE_TEXT()))
                    name to scenario
                    println(Ansi.colorize("\tOK", Attribute.BOLD(), Attribute.GREEN_TEXT()))
                }
        }
        println()
    }
}

private fun resolvePath(filePath: String) =
    Paths.get(System.getenv("GRAMLIN_HOME") ?: ".", filePath).absolutePathString()

private val propKeys = mapOf(
    "api_id" to "Please obtain your app_id and api_hash from https://my.telegram.org/apps and paste it to the tdbot.properties file",
    "api_hash" to "Please obtain your app_id and api_hash from https://my.telegram.org/apps and paste it to the tdbot.properties file",
    "bot_token" to "Please register your own Telegram bot via https://t.me/BotFather and paste its token to the tdbot.properties file"
)