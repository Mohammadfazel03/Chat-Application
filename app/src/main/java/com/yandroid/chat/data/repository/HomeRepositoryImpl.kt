package com.yandroid.chat.data.repository

import android.util.Log
import androidx.datastore.core.DataStore
import com.yandroid.chat.SettingPreferences
import com.yandroid.chat.data.model.MessageDto
import com.yandroid.chat.data.model.User
import com.yandroid.chat.data.remote.socket.SocketService
import com.yandroid.chat.data.remote.socket.SocketServiceImpl
import com.yandroid.chat.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import java.io.IOException

class HomeRepositoryImpl(
    private val dataStore: DataStore<SettingPreferences>,
    private val socketService: SocketService
):HomeRepository {


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

    override suspend fun sendMessage(message: MessageDto) = socketService.sendMessage(message)

    override fun observeMessages(scope: CoroutineScope): Flow<MessageDto> = socketService.observeMessages(scope)

    override suspend fun closeSession() = socketService.closeSession()

    override suspend fun getSession(token: String): Resource<Unit> = socketService.getSession(token)

    override suspend fun fitchInitialPreferences():String {
        return dataStore.data.first().token
    }
}