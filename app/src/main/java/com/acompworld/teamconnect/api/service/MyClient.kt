package com.acompworld.teamconnect.api.service

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object MyClient {

    val okhttpBuilder = OkHttpClient.Builder()
        .connectTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(120, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)

    private  val retrofitBuilder = Retrofit.Builder()
        .baseUrl("http://teamconnect.acolabz.com:3003/api/")
        .addConverterFactory(MoshiConverterFactory.create())

    val api = retrofitBuilder
        .client(okhttpBuilder.build())
        .build()
        .create(TeamConnectApi::class.java)
}