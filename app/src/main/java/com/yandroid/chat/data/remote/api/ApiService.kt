package com.yandroid.chat.data.remote.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

}

fun createApiServiceInstance(): ApiService = Retrofit.Builder()
    .baseUrl("https://192.168.1.3:8000/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build().create(ApiService::class.java)
