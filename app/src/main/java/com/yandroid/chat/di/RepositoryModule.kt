package com.yandroid.chat.di

import com.yandroid.chat.data.repository.HomeRepository
import com.yandroid.chat.data.repository.HomeRepositoryImpl
import com.yandroid.chat.data.repository.SignupRepository
import com.yandroid.chat.data.repository.SignupRepositoryImpl
import org.koin.dsl.module

val repositoryModules = module {
    factory<SignupRepository> { SignupRepositoryImpl(get(),get()) }
    factory<HomeRepository> { HomeRepositoryImpl(get()) }
}