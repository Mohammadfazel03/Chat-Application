package com.yandroid.chat.view.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yandroid.chat.data.model.User
import com.yandroid.chat.data.repository.HomeRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel(private val repository: HomeRepository) : ViewModel() {

    val data = repository.settingPreferencesFlow().asLiveData()

}