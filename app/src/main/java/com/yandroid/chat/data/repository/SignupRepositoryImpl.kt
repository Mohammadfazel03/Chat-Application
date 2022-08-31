package com.yandroid.chat.data.repository

import androidx.datastore.core.DataStore
import com.yandroid.chat.SettingPreferences
import com.yandroid.chat.data.model.User
import com.yandroid.chat.data.remote.api.ApiService

class SignupRepositoryImpl(
    private val apiService: ApiService,
    private val dataStore: DataStore<SettingPreferences>
) : SignupRepository {

    override suspend fun signup(username: String, email: String, password: String): User {
        return apiService.signup(
            username = username,
            email = email,
            password = password
        )
    }

    override suspend fun login(username: String, password: String): User {
        return apiService.login(
            username = username,
            password = password
        )
    }

    override suspend fun saveUserLogin(user: User) {
        dataStore.updateData { preferences ->
            preferences.toBuilder()
                .setToken(user.token)
                .setUsername(user.username)
                .setEmail(user.email)
                .build()
        }
    }

}