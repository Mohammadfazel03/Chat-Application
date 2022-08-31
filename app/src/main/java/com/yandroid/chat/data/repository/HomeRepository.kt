package com.yandroid.chat.data.repository

import com.yandroid.chat.SettingPreferences
import com.yandroid.chat.data.model.User
import kotlinx.coroutines.flow.Flow

interface HomeRepository {

    fun settingPreferencesFlow(): Flow<SettingPreferences>

}