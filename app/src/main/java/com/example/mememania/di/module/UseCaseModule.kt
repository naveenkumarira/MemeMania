package com.example.mememania.di.module

import com.example.mememania.core.usecase.MemeUseCase
import com.example.mememania.core.usecase.MemeUseCaseImpl
import com.example.mememania.data.MemeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCaseModule {
    @Provides
    fun getMemeUseCaseImpl(repository: MemeRepository): MemeUseCase = MemeUseCaseImpl(repository)
}