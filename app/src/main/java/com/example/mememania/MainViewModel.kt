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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(val memeUseCase: MemeUseCase) : ViewModel() {

    var movieListResponse: List<Meme> by mutableStateOf(listOf())
    private var errorMessage: String by mutableStateOf("")

    fun getMemeList() {
        viewModelScope.launch {
            try {
                val movieList = memeUseCase.getMeme()
                movieListResponse = movieList
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }

    fun performLike(meme: Meme) {
        viewModelScope.launch {
            memeUseCase.updateMeme(meme)
            movieListResponse = memeUseCase.getMeme()
        }
    }
}