package com.example.mememania.core.usecase

import com.example.mememania.data.MemeRepository
import com.example.mememania.data.network.Meme

class MemeUseCaseImpl(val repository: MemeRepository): MemeUseCase {
    override suspend fun getMeme(): List<Meme> {
        return repository.getMeme()
    }

    override suspend fun updateMeme(meme: Meme) {
        repository.performLike(meme = meme)
    }
}