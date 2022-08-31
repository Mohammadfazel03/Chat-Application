package com.yandroid.chat.data.remote.api

import com.yandroid.chat.data.model.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("user/")
    @FormUrlEncoded
    suspend fun signup(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("email") email: String
    ): User

    @POST("auth/")
    @FormUrlEncoded
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
    ): User

}

fun createApiServiceInstance(): ApiService = Retrofit.Builder()
    .baseUrl("http://192.168.1.2:8000/api/")
    .addConverterFactory(GsonConverterFactory.create())
    .build().create(ApiService::class.java)
