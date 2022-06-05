package com.example.mememania

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mememania.core.usecase.MemeUseCase
import com.example.mememania.core.usecase.MemeUseCaseImpl
import com.example.mememania.data.MemeRepository
import com.example.mememania.data.MemeRepositoryImpl
import com.example.mememania.data.local.MemeDatabase
import com.example.mememania.data.network.Meme
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var movieListResponse: List<Meme> by mutableStateOf(listOf())
    private lateinit var memeRepo: MemeRepository
    private lateinit var memeUseCase: MemeUseCase
    var errorMessage: String by mutableStateOf("")

    fun initViewModel(application: Application) {
        memeRepo = MemeRepositoryImpl(
            MemeDatabase.getDatabase(application).memeDao(),
            MemeApiService.getInstance()
        )
        memeUseCase = MemeUseCaseImpl(repository = memeRepo)
    }

    fun getMemeList() {
        viewModelScope.launch {
            memeUseCase.getMeme()
            try {
                val movieList = memeRepo.getMeme()
                movieListResponse = movieList
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun performLike(meme: Meme) {
        viewModelScope.launch {
            memeRepo.performLike(meme)
            movieListResponse = memeRepo.getMeme()
        }
    }
}