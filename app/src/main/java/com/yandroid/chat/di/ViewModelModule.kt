package com.yandroid.chat.di

import com.yandroid.chat.view.home.HomeViewModel
import com.yandroid.chat.view.login.LoginViewModel
import com.yandroid.chat.view.signup.SignupViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModules = module {
    viewModel<SignupViewModel> { SignupViewModel(get()) }
    viewModel<HomeViewModel> { HomeViewModel(get()) }
    viewModel<LoginViewModel> { LoginViewModel(get()) }
}