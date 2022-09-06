package com.yandroid.chat.data.remote.socket

import android.util.Log
import com.yandroid.chat.data.model.MessageDto
import com.yandroid.chat.utils.Resource
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.websocket.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ClosedReceiveChannelException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.isActive
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class SocketServiceImpl(
    private val client: HttpClient
) : SocketService {


    private var socket: WebSocketSession? = null


    override suspend fun getSession(token: String): Resource<Unit> {
        return try {
            socket = client.webSocketSession {
                url("${SocketService.Endpoints.ChatSocket.url}?$token")
            }
            if (socket?.isActive == true) {
                Resource.Success(Unit)
            } else Resource.Error("Couldn't establish a connection.")
        } catch (e: Exception) {
            Log.i("TAG", "getSession: ${e.message}")
            e.printStackTrace()
            Resource.Error(e.localizedMessage ?: "Unknown error")
        } catch (e: ClosedReceiveChannelException) {
            Log.i("TAG", "2getSession: ")
            Resource.Error(e.localizedMessage ?: "Unknown error")
        }
    }

    override suspend fun sendMessage(message: MessageDto) {
        try {
            socket?.send(Frame.Text(Json.encodeToString(message)))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun observeMessages(scope: CoroutineScope): Flow<MessageDto> {
        return try {
            socket?.incoming
                ?.receiveAsFlow()
                ?.shareIn(scope, SharingStarted.Eagerly)
                ?.filter { it is Frame.Text }
                ?.map {
                    val json = (it as? Frame.Text)?.readText() ?: ""
                    val messageDto = Json.decodeFromString<MessageDto>(json)
                    messageDto
                } ?: flow { }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i("TAG", "observeMessages: gfvv")
            flow { }
        }  catch (e: ClosedReceiveChannelException) {
            Log.i("TAG", "22getSession: ")
            flow {  }
        }
    }

    override suspend fun closeSession() {
        socket?.close()
    }
}