package com.tdbot.runtime

import it.tdlight.client.ClientInteraction
import it.tdlight.client.InputParameter
import it.tdlight.client.ParameterInfo
import it.tdlight.client.ParameterInfoNotifyLink
import org.slf4j.LoggerFactory
import java.util.function.Consumer

class LinkClientInteraction(private val settings: TdRuntime.Settings): ClientInteraction {
    private val logger = LoggerFactory.getLogger(javaClass.simpleName)

    var link: String? = null
        private set

    val publicUrl
        get() = link?.replace("tg://login", settings.publicQrLink)

    override fun onParameterRequest(
        parameter: InputParameter?,
        parameterInfo: ParameterInfo?,
        result: Consumer<String>?
    ) {
        if (parameter == InputParameter.NOTIFY_LINK) {
            val param = parameterInfo as ParameterInfoNotifyLink
            logger.info("Auth link received ${param.link}")
            link = param.link
        }

        result?.accept("")
    }
}