package com.yandroid.chat.data.local

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import com.yandroid.chat.SettingPreferences
import java.io.InputStream
import java.io.OutputStream

object SettingPreferencesSerialize : Serializer<SettingPreferences> {
    override val defaultValue: SettingPreferences
        get() = SettingPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): SettingPreferences {
        try {
            return SettingPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: SettingPreferences, output: OutputStream) = t.writeTo(output)
}