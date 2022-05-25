package com.example.mememania.network


import com.google.gson.annotations.SerializedName

/**
 *
{
"success": true,
"data": {}
}
 */
data class MemeResponse(
    @SerializedName("data")
    val data: Data?,
    @SerializedName("success")
    val success: Boolean?
)