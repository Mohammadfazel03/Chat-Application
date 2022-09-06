package com.yandroid.chat.utils

enum class ConnectionState(val connectionState: String) {
    WAITING_NETWORK("Waiting for network..."),
    CONNECTING("Connecting..."),
    CONNECTED(""),
    UPDATING("updating...")
}