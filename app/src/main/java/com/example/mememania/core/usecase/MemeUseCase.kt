package com.example.mememania.core.usecase

import com.example.mememania.data.network.Meme

interface MemeUseCase {
    suspend fun getMeme(): List<Meme>
    suspend fun updateMeme(meme: Meme)
}