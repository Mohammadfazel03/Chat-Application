package com.yandroid.chat.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username")
    val username: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("token")
    val token: String
)
