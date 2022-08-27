package com.yandroid.chat

import android.app.Application
import android.util.Log
import okhttp3.*
import okio.ByteString
import org.greenrobot.eventbus.EventBus

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        val httpClient = OkHttpClient()
        val webSocketUrl = "ws://192.168.1.4:8000/ws/chat/?9c19e3cfe243d519feb3dacd742f3817d8a3b4ad"
        val request = Request.Builder()
            .url(webSocketUrl)
            .build()

        httpClient.newWebSocket(request, object : WebSocketListener() {
            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {

                super.onClosed(webSocket, code, reason)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
            }

            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.i("TAG", "onOpen:  ")
                EventBus.getDefault().post(Event.onMessage())
                super.onOpen(webSocket, response)
            }
        })
    }
}