package com.acompworld.teamconnect.di

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.acompworld.teamconnect.api.service.TeamConnectApi
import com.acompworld.teamconnect.repo.TeamConnectRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() = Retrofit.Builder()
        .baseUrl("http://teamconnect.acolabz.com:3003/api/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(TeamConnectApi::class.java)


    @Provides
    @Singleton
    fun provideRepo(api: TeamConnectApi) = TeamConnectRepository(api)

    @Provides
    @Singleton
    fun ProvideConnectivityManager(app: Application) =
        app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

}