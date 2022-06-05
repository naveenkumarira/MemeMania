package com.example.mememania.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mememania.data.network.Meme
import kotlinx.parcelize.Parcelize

@Entity(tableName = "saved_meme")
data class SavedMeme(
    @PrimaryKey @ColumnInfo(name = "meme_id")
    val id: Int,
    @ColumnInfo(name = "image")
    val imageUrl: String?,
    @ColumnInfo(name = "is_liked")
    val isLiked: Boolean,
    @ColumnInfo(name = "title")
    val title: String?
)

fun SavedMeme.mapToMeme(): Meme = Meme(
    id = id.toString(),
    name = title,
    url = imageUrl,
    isLiked = isLiked,
    height = null,
    width = null,
    boxCount = null
)