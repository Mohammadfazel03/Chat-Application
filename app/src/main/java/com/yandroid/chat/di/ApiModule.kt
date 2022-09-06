package com.yandroid.chat.di


import com.yandroid.chat.data.remote.api.ApiService
import com.yandroid.chat.data.remote.api.createApiServiceInstance
import com.yandroid.chat.data.remote.socket.SocketService
import com.yandroid.chat.data.remote.socket.SocketServiceImpl
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.kotlinx.*
import kotlinx.serialization.json.Json
import org.koin.dsl.module


val apiModules = module {

    fun createHttpClient(): HttpClient {
        return HttpClient() {
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }
        }
    }

    single<ApiService> { createApiServiceInstance() }
    single<HttpClient> { createHttpClient() }
    single<SocketService> { SocketServiceImpl(get()) }
}