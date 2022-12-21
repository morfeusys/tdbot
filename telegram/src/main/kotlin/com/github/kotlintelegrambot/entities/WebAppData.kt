package com.github.kotlintelegrambot.entities

import com.google.gson.annotations.SerializedName

data class WebAppData(
    val data: String,
    @SerializedName("button_text") val buttonText: String
)
