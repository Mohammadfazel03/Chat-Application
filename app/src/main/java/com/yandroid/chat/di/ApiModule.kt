package com.yandroid.chat.di


import com.yandroid.chat.data.remote.api.ApiService
import com.yandroid.chat.data.remote.api.createApiServiceInstance
import org.koin.dsl.module


val apiModules = module {
    single<ApiService> { createApiServiceInstance() }
}