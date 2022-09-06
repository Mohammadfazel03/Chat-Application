package com.yandroid.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Message(
    private val type:Int,
    private val message:String,
)