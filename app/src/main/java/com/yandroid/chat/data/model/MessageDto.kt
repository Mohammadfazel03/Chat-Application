package com.yandroid.chat.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class MessageDto(
    val type:String,
    val message: Message,
    val chat:String,
    val sender:User,
    @SerialName("Error")
    val error:String
)