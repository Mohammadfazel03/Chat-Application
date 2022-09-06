package com.yandroid.chat.view.home

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import androidx.lifecycle.*
import com.yandroid.chat.data.repository.HomeRepository
import com.yandroid.chat.utils.ConnectionState
import com.yandroid.chat.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: HomeRepository,
    application: Application
) : AndroidViewModel(application) {

    val settingPreferences = repository.settingPreferencesFlow().asLiveData()

    private val _connectionState =
        MutableLiveData<String>(ConnectionState.WAITING_NETWORK.connectionState)

    val connectionState: LiveData<String> = _connectionState

    private val connectivityManager =
        application.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            socketConnection()
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            _connectionState.postValue(ConnectionState.WAITING_NETWORK.connectionState)
        }

        override fun onUnavailable() {
            super.onUnavailable()
            _connectionState.postValue(ConnectionState.WAITING_NETWORK.connectionState)
        }
    }

    init {
        networkConnection()
    }

    fun networkConnection() {
        val builder = NetworkRequest.Builder()
        connectivityManager.registerNetworkCallback(
            builder
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build(),
            networkCallback
        )
    }

    private fun socketConnection() {
        _connectionState.postValue(ConnectionState.CONNECTING.connectionState)
        viewModelScope.launch(Dispatchers.IO) {
            val token = repository.fitchInitialPreferences()
            val result = repository.getSession(token)
            when (result) {
                is Resource.Success -> {
                    _connectionState.postValue(ConnectionState.CONNECTED.connectionState)
                }
                is Resource.Error -> {
                    Log.i("TAG", "connect: ${result.message}")
                }
            }
        }
    }

    private fun disconnect() {
        viewModelScope.launch {
            repository.closeSession()
        }
    }

    override fun onCleared() {
        super.onCleared()
        disconnect()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}