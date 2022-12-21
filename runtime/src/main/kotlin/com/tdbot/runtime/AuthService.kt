package com.tdbot.runtime

import it.tdlight.client.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import java.util.concurrent.CompletableFuture
import java.util.function.Consumer

class AuthService : ClientInteraction, CoroutineScope {
    override val coroutineContext = Dispatchers.IO

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
        launch { phoneNumber.complete(phone) }
    }

    fun setConfirmationCode(code: String) = launch {
        confirmationCode.complete(code)
    }

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