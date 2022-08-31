package com.yandroid.chat.data.repository

import com.yandroid.chat.data.model.User

interface SignupRepository {

    suspend fun signup(username: String, email: String, password: String): User

    suspend fun login(username: String, password: String): User

    suspend fun saveUserLogin(user: User)
}