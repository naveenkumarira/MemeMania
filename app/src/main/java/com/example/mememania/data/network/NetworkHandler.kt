package com.example.mememania.data.network

import com.example.mememania.MemeApiService
import com.google.gson.Gson
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkHandler {

    private var BASE_URL: String = "https://api.imgflip.com/"
    private lateinit var retrofit: Retrofit

    fun init(cache: Cache? = null) {
        val okHttpClient = createOkHttpClient()
        if (cache != null)
            okHttpClient.cache(cache)
        val gsonConverter = GsonConverterFactory.create(Gson())
        val client = okHttpClient.build()
        retrofit = createRetrofitInstance(BASE_URL, gsonConverter, client)
    }

    private fun createOkHttpClient(): OkHttpClient.Builder {
        return OkHttpClient().newBuilder().connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS).readTimeout(10, TimeUnit.SECONDS)
    }

    /**
     * create instance of retrofit
     */
    private fun createRetrofitInstance(
        url: String,
        gsonConverter: GsonConverterFactory,
        client: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(gsonConverter)
            .client(client).build()
    }

    fun getMemeService(): MemeApiService {
        return retrofit.create(MemeApiService::class.java)
    }

    fun getRetrofitInstance(): Retrofit {
        return retrofit
    }
}
