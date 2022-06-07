package com.example.mememania.di.module

import com.example.mememania.MemeApiService
import com.example.mememania.data.network.NetworkHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideNetworkHandler() = NetworkHandler

    @Provides
    fun getApiService() = NetworkHandler.getMemeService()
}
