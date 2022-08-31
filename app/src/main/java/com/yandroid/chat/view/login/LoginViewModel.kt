package com.yandroid.chat.view.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandroid.chat.data.model.User
import com.yandroid.chat.data.repository.SignupRepository
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class LoginViewModel(private val signupRepository: SignupRepository) : ViewModel() {

    val isProgress = MutableLiveData<Boolean>(false)
    val user = MutableLiveData<User?>()
    val errorMessage = MutableLiveData<String?>()

    fun login(username: String, password: String) {
        isProgress.postValue(true)
        errorMessage.postValue(null)
        user.postValue(null)
        viewModelScope.launch(Dispatchers.IO + CoroutineExceptionHandler { _, th ->
            errorMessage.postValue(
                (th as? HttpException)?.response()?.errorBody()?.string() ?: th.message
            )
            isProgress.postValue(false)
        }) {
            val result =
                signupRepository.login(username = username, password = password)
            signupRepository.saveUserLogin(result)
            user.postValue(result)
            isProgress.postValue(false)
        }
    }

}