package com.yandroid.chat.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.yandroid.chat.SettingPreferences
import com.yandroid.chat.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException

class HomeRepositoryImpl(private val dataStore: DataStore<SettingPreferences>):HomeRepository {


    override fun settingPreferencesFlow(): Flow<SettingPreferences> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.i("TAG", "Error reading sort order preferences.", exception)
                emit(SettingPreferences.getDefaultInstance())
            } else {
                throw exception
            }
        }
}