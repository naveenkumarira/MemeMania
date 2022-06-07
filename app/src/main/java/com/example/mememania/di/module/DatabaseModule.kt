package com.example.mememania.di.module

import android.content.Context
import com.example.mememania.data.local.MemeDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = MemeDatabase.getDatabase(context)

    @Provides
    fun provideMemeDatabase(database: MemeDatabase) = database.memeDao()
}
