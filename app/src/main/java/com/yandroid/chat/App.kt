package com.yandroid.chat

import android.app.Application
import android.util.Log
import com.yandroid.chat.di.apiModules
import com.yandroid.chat.di.localModules
import com.yandroid.chat.di.repositoryModules
import com.yandroid.chat.di.viewModelModules
import okhttp3.*
import okio.ByteString
import org.greenrobot.eventbus.EventBus
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(
                apiModules,
                repositoryModules,
                viewModelModules,
                localModules
            )
        }

    }
}