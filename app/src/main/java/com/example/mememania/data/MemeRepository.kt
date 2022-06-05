package com.example.mememania.data

import com.example.mememania.data.network.Meme

interface MemeRepository {
    suspend fun saveMeme(memes: List<Meme>): Unit
    suspend fun fetchMemes(): List<Meme>
    suspend fun getMeme(): List<Meme>
}