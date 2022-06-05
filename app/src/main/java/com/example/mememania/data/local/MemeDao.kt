package com.example.mememania.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MemeDao {
    @Query("SELECT * from saved_meme ORDER BY meme_id ASC")
    abstract suspend fun getAllSavedMeme(): List<SavedMeme>

    @Query("SELECT * from saved_meme WHERE is_liked = 1")
    abstract suspend fun getAllLikedMeme(): List<SavedMeme>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    abstract suspend fun insertMemes(memes: List<SavedMeme>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertMeme(meme: SavedMeme)
}