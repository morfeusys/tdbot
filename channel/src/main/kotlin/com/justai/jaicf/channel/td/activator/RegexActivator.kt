package com.justai.jaicf.channel.td.activator

import com.justai.jaicf.activator.ActivatorFactory
import com.justai.jaicf.activator.BaseActivator
import com.justai.jaicf.activator.regex.RegexActivationRule
import com.justai.jaicf.activator.regex.RegexActivatorContext
import com.justai.jaicf.api.BotRequest
import com.justai.jaicf.api.hasQuery
import com.justai.jaicf.context.BotContext
import com.justai.jaicf.model.scenario.ScenarioModel
import java.util.regex.Pattern

val String.asPattern
    get() = Pattern.compile(this, Pattern.CASE_INSENSITIVE or Pattern.UNICODE_CASE or Pattern.MULTILINE or Pattern.DOTALL)

class RegexActivator(model: ScenarioModel) : BaseActivator(model) {

    override val name = "regexActivator"

    override fun canHandle(request: BotRequest) = request.hasQuery()

    override fun provideRuleMatcher(botContext: BotContext, request: BotRequest) =
        ruleMatcher<RegexActivationRule> { rule ->
            val pattern = rule.regex.asPattern
            val matcher = pattern.matcher(request.input)
            if (matcher.matches()) {
                RegexActivatorContext(pattern, matcher)
            } else {
                null
            }
        }

    companion object : ActivatorFactory {
        override fun create(model: ScenarioModel) = RegexActivator(model)
    }
}