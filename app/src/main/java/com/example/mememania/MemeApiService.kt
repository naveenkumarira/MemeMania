package com.example.mememania

import com.example.mememania.data.network.MemeResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface MemeApiService {
    @GET("get_memes")
    suspend fun getMemes(): MemeResponse

    companion object {
        var memeApiService: MemeApiService? = null
        fun getInstance(): MemeApiService {
            if (memeApiService == null) {
                memeApiService = Retrofit.Builder()
                    .baseUrl("https://api.imgflip.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(MemeApiService::class.java)
            }
            return memeApiService!!
        }
    }
}