package com.example.mememania.network


import com.google.gson.annotations.SerializedName

/**
 *
"id": "181913649",
"name": "Drake Hotline Bling",
"url": "https://i.imgflip.com/30b1gx.jpg",
"width": 1200,
"height": 1200,
"box_count": 2
 */
data class Meme(
    @SerializedName("box_count")
    val boxCount: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("width")
    val width: Int?
)