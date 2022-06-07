package com.example.mememania.di.module

import com.example.mememania.MemeApiService
import com.example.mememania.data.MemeRepository
import com.example.mememania.data.MemeRepositoryImpl
import com.example.mememania.data.local.MemeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun getRemoteRepository(memeDao: MemeDao, apiService: MemeApiService): MemeRepository =
        MemeRepositoryImpl(memeDao, apiService)
}
