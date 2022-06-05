package com.example.mememania.data.network


import com.google.gson.annotations.SerializedName

/**
 *
"data": {
"memes": []
}
 */
data class Data(
    @SerializedName("memes")
    val memes: List<Meme>?
)