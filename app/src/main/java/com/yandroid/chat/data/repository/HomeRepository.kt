package com.yandroid.chat.data.repository

import com.yandroid.chat.SettingPreferences
import com.yandroid.chat.data.model.MessageDto
import com.yandroid.chat.data.model.User
import com.yandroid.chat.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun settingPreferencesFlow(): Flow<SettingPreferences>

    suspend fun sendMessage(message: MessageDto)

    fun observeMessages(scope: CoroutineScope): Flow<MessageDto>

    suspend fun closeSession()

    suspend fun getSession(
        token: String
    ): Resource<Unit>

    suspend fun fitchInitialPreferences() :String
}