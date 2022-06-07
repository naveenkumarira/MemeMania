package com.example.mememania.data

import android.util.Log
import com.example.mememania.MemeApiService
import com.example.mememania.data.local.MemeDao
import com.example.mememania.data.local.mapToMeme
import com.example.mememania.data.network.Meme
import com.example.mememania.data.network.mapToSavedMeme
import javax.inject.Inject

class MemeRepositoryImpl @Inject constructor(
    private val memeDao: MemeDao,
    private val memeApiService: MemeApiService
) :
    MemeRepository {
    override suspend fun saveMeme(memes: List<Meme>): Unit =
        memeDao.insertMemes(memes.map { it.mapToSavedMeme() })

    override suspend fun fetchMemes(): List<Meme> {
        return try {
            val movieList = memeApiService.getMemes().data?.memes ?: listOf()
            memeApiService.getMemes()
            if (movieList.isNotEmpty()) {
                saveMeme(movieList)
            }
            movieList
        } catch (e: Exception) {
            Log.d("MemeRepository", e.localizedMessage)
            listOf()
        }
    }

    override suspend fun getMeme(): List<Meme> {
        val memelist = memeDao.getAllSavedMeme()
        return if (memelist.isEmpty()) {
            fetchMemes()
        } else {
            memelist.map {
                it.mapToMeme()
            }
        }
    }

    override suspend fun performLike(meme: Meme) {
        memeDao.insertMeme(meme = meme.copy(isLiked = !meme.isLiked).mapToSavedMeme())
    }
}