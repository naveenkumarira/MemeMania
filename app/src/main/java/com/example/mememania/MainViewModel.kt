package com.example.mememania

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mememania.network.Meme
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    var movieListResponse: List<Meme> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")

    fun getMemeList() {
        viewModelScope.launch {
            val apiService = ApiService.getInstance()
            try {
                val movieList = apiService.getMemes().data?.memes ?: listOf()
                movieListResponse = movieList
            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}