package com.justai.gramlin.server

import com.github.mustachejava.DefaultMustacheFactory
import com.justai.jaicf.channel.td.request.DefaultTdRequest
import java.io.StringWriter
import java.util.*

private val mustacheFactory = DefaultMustacheFactory()

private val String.asTemplate
    get() = mustacheFactory.compile(reader(), this)

private val defaultScope get() = mutableMapOf<String, Any>(
    "timestamp" to System.currentTimeMillis(),
    "unixtime" to System.currentTimeMillis() / 1000,
    "uuid" to UUID.randomUUID().toString(),
    "env" to System.getenv(),
)

private val DefaultTdRequest.asTemplateScope
    get() = mapOf(
        "update" to update,
        "me" to me
    )

fun String.executeTemplate(scope: Map<String, Any> = emptyMap()) = StringWriter().let { writer ->
    asTemplate.execute(writer, defaultScope + scope)
    writer.flush()
    writer.toString()
}

fun String.executeTemplate(request: DefaultTdRequest) =
    executeTemplate(request.asTemplateScope)