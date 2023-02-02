package com.justai.jaicf.channel.td.activator

import com.justai.jaicf.activator.ActivatorFactory
import com.justai.jaicf.activator.BaseActivator
import com.justai.jaicf.activator.regex.RegexActivationRule
import com.justai.jaicf.activator.regex.RegexActivatorContext
import com.justai.jaicf.api.BotRequest
import com.justai.jaicf.channel.td.request.messageRequest
import com.justai.jaicf.channel.td.request.td
import com.justai.jaicf.channel.td.text
import com.justai.jaicf.context.BotContext
import com.justai.jaicf.model.scenario.ScenarioModel
import java.util.regex.Pattern

val String.asPattern
    get() = Pattern.compile(this, Pattern.CASE_INSENSITIVE or Pattern.UNICODE_CASE or Pattern.MULTILINE or Pattern.DOTALL)

class TdMessageRegexActivator(model: ScenarioModel) : BaseActivator(model) {

    override val name = "tdMessageRegexActivator"

    override fun canHandle(request: BotRequest) = request.td?.messageRequest != null

    override fun provideRuleMatcher(botContext: BotContext, request: BotRequest) =
        ruleMatcher<RegexActivationRule> { rule ->
            val text = request.td?.messageRequest?.content?.text
            if (text != null) {
                val pattern = rule.regex.asPattern
                val matcher = pattern.matcher(text)
                if (matcher.matches()) {
                    RegexActivatorContext(pattern, matcher)
                } else {
                    null
                }
            } else {
                null
            }
        }

    companion object : ActivatorFactory {
        override fun create(model: ScenarioModel) = TdMessageRegexActivator(model)
    }
}