package com.yandroid.chat.data.remote.socket

import com.yandroid.chat.data.model.MessageDto
import com.yandroid.chat.utils.Resource
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface SocketService {

    suspend fun getSession(
        token: String
    ): Resource<Unit>

    suspend fun sendMessage(message: MessageDto)

    fun observeMessages(scope: CoroutineScope): Flow<MessageDto>

    suspend fun closeSession()

    companion object {
        const val BASE_URL = "ws://192.168.1.2:8000"
    }

    sealed class Endpoints(val url: String) {
        object ChatSocket: Endpoints("$BASE_URL/ws/chat/")
    }


}