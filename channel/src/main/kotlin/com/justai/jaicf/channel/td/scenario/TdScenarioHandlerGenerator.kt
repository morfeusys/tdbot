package com.justai.jaicf.channel.td.scenario

import it.tdlight.jni.TdApi
import org.reflections.Reflections
import java.io.File

fun main() {
    val ref = Reflections("it.tdlight.jni")

    val messages = ref.getSubTypesOf(TdApi.MessageContent::class.java)
        .map { it.simpleName.substring(7) }
        .filterNot { it == "Text" }
        .sorted()

    val updates = ref.getSubTypesOf(TdApi.Update::class.java)
        .map { it.simpleName }.sorted()

    val file = File("./channel/src/main/kotlin/com/justai/jaicf/channel/td/scenario/TdScenarioHandlers.kt")
    file.writeText("package com.justai.jaicf.channel.td.scenario\n" +
            "\n" +
            "import com.justai.jaicf.builder.ScenarioDsl\n" +
            "import com.justai.jaicf.channel.td.OnlyIf\n" +
            "import com.justai.jaicf.channel.td.TdNewMessageRequest\n" +
            "import com.justai.jaicf.channel.td.TdReactions\n" +
            "import com.justai.jaicf.channel.td.TdRequest\n" +
            "import com.justai.jaicf.context.ActionContext\n" +
            "import com.justai.jaicf.context.ActivatorContext\n" +
            "import com.justai.jaicf.plugin.StateBody\n" +
            "import com.justai.jaicf.plugin.StateDeclaration\n" +
            "import it.tdlight.jni.TdApi\n")

    messages.forEach { msg ->
        file.appendText("\n@ScenarioDsl\n" +
                "@StateDeclaration\n" +
                "fun TdScenarioRootBuilder.on${msg}Message(\n" +
                "    vararg conditions: OnlyIf,\n" +
                "    @StateBody body: ActionContext<ActivatorContext, TdNewMessageRequest<TdApi.Message${msg}>, TdReactions>.() -> Unit\n" +
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