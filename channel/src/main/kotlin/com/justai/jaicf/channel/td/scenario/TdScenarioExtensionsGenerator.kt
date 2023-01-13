package com.justai.jaicf.channel.td.scenario

import it.tdlight.jni.TdApi
import org.reflections.Reflections
import java.io.File

fun main() {
    val ref = Reflections("it.tdlight.jni")

    val messages = ref.getSubTypesOf(TdApi.MessageContent::class.java)
        .map { it.simpleName.substring(7) }
        .sorted()

    val updates = ref.getSubTypesOf(TdApi.Update::class.java)
        .map { it.simpleName }.sorted()

    fun generateHandlers() {
        val file = File("./channel/src/main/kotlin/com/justai/jaicf/channel/td/scenario/TdScenarioHandlers.kt")
        file.writeText("package com.justai.jaicf.channel.td.scenario\n\n" +
                "import com.justai.jaicf.builder.ScenarioDsl\n" +
                "import com.justai.jaicf.channel.td.*\n" +
                "import com.justai.jaicf.context.ActionContext\n" +
                "import com.justai.jaicf.context.ActivatorContext\n" +
                "import com.justai.jaicf.plugin.StateBody\n" +
                "import com.justai.jaicf.plugin.StateDeclaration\n" +
                "import com.justai.jaicf.channel.td.request.TdMessageRequest\n" +
                "import com.justai.jaicf.channel.td.request.TdRequest\n" +
                "import it.tdlight.jni.TdApi\n")

        messages.forEach { msg ->
            file.appendText("\n@ScenarioDsl\n" +
                    "@StateDeclaration\n" +
                    "fun TdScenarioRootBuilder.on${msg}Message(\n" +
                    "    vararg conditions: OnlyIf,\n" +
                    "    @StateBody body: ActionContext<ActivatorContext, TdMessageRequest<TdApi.Message${msg}>, TdReactions>.() -> Unit\n" +
                    ") = onNewMessage(conditions = conditions, body = body)\n")
        }

        updates.forEach { update ->
            file.appendText("\n@ScenarioDsl\n" +
                    "@StateDeclaration\n" +
                    "fun TdScenarioRootBuilder.on${update}(\n" +
                    "    vararg conditions: OnlyIf,\n" +
                    "    @StateBody body: ActionContext<ActivatorContext, TdRequest<TdApi.${update}>, TdReactions>.() -> Unit\n" +
                    ") = onUpdate(conditions = conditions, body = body)\n")
        }
    }

    fun generateActivators() {
        val file = File("./channel/src/main/kotlin/com/justai/jaicf/channel/td/scenario/TdScenarioActivators.kt")
        file.writeText("package com.justai.jaicf.channel.td.scenario\n\n" +
                "import com.justai.jaicf.builder.ActivationRulesBuilder\n" +
                "import it.tdlight.jni.TdApi\n")

        messages.forEach { msg ->
            file.appendText("\nfun ActivationRulesBuilder.td${msg}Message() = tdMessage<TdApi.Message$msg>()\n")
        }

        updates.forEach { update ->
            file.appendText("\nfun ActivationRulesBuilder.td$update() = tdUpdate<TdApi.$update>()\n")
        }
    }

    fun generateTypeTokens() {
        val file = File("./channel/src/main/kotlin/com/justai/jaicf/channel/td/scenario/TdScenarioTypeTokens.kt")
        file.writeText("package com.justai.jaicf.channel.td.scenario\n\n" +
                "import com.justai.jaicf.channel.td.tdMessageToken\n" +
                "import com.justai.jaicf.channel.td.tdUpdateToken\n" +
                "import it.tdlight.jni.TdApi\n")

        messages.forEach { msg ->
            file.appendText("\nval td${msg}MessageType = tdMessageToken<TdApi.Message$msg>()\n")
        }

        updates.forEach { update ->
            file.appendText("\nval td${update}Type = tdUpdateToken<TdApi.$update>()\n")
        }
    }

    fun generateRequestExtensions() {
        val file = File("./channel/src/main/kotlin/com/justai/jaicf/channel/td/request/TdRequestExtensions.kt")
        file.writeText("package com.justai.jaicf.channel.td.request\n\n" +
                "import it.tdlight.jni.TdApi\n")

        messages.forEach { msg ->
            val value = msg.replaceFirstChar { it.lowercase() }
            file.appendText("\nval DefaultTdRequest.${value}MessageRequest get() = this as? TdMessageRequest<TdApi.Message$msg>\n")
        }

        updates.forEach { update ->
            val value = update.replaceFirstChar { it.lowercase() }
            file.appendText("\nval DefaultTdRequest.${value}Request get() = this as? TdRequest<TdApi.$update>\n")
        }
    }

    generateHandlers()
    generateActivators()
    generateTypeTokens()
    generateRequestExtensions()
}