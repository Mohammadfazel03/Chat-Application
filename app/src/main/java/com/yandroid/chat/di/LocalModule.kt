package com.yandroid.chat.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import com.yandroid.chat.SettingPreferences
import com.yandroid.chat.data.local.SettingPreferencesSerialize
import com.yandroid.chat.data.remote.api.ApiService
import com.yandroid.chat.data.remote.api.createApiServiceInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.dsl.module

private const val DATA_STORE_FILE_NAME = "setting_prefs.pb"

val localModules = module {



    fun provideProtoDataStore(context: Context): DataStore<SettingPreferences> {
        return DataStoreFactory.create(
            serializer = SettingPreferencesSerialize,
            produceFile = { context.dataStoreFile(DATA_STORE_FILE_NAME) },
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }

    single<DataStore<SettingPreferences>> { provideProtoDataStore(get()) }
}