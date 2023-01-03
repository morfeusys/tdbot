package com.tdbot.runtime

import it.tdlight.client.AuthenticationData
import it.tdlight.client.ClientInteraction
import it.tdlight.client.InputParameter
import it.tdlight.client.ParameterInfo
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class AuthService : ClientInteraction {
    private val logger = LoggerFactory.getLogger(javaClass)

    private lateinit var phoneNumber: CompletableFuture<Long>
    private lateinit var confirmationCode: CompletableFuture<String>

    var userId: Long = 0
        private set

    val authData = object : AuthenticationData {
        override fun isQrCode() = false
        override fun isBot() = false
        override fun getBotToken() = null

        override fun getUserPhoneNumber(): Long {
            logger.info("Awaiting phone number")
            phoneNumber = CompletableFuture()
            return phoneNumber.get()
        }
    }

    fun setPhoneNumber(userId: Long, phone: Long) {
        this.userId = userId
        phoneNumber.complete(phone)
    }

    fun setConfirmationCode(code: String) =
        confirmationCode.complete(code)


    override fun onParameterRequest(
        parameter: InputParameter?,
        parameterInfo: ParameterInfo?,
        result: Consumer<String>?
    ) {
        if (parameter == InputParameter.ASK_CODE) {
            logger.info("Awaiting confirmation code")
            confirmationCode = CompletableFuture()
            result?.accept(confirmationCode.get())
        }
    }
}