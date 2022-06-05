package com.example.mememania

import android.app.Application
import com.example.mememania.data.local.MemeDatabase

class MemeManiaApplication() : Application() {
    override fun onCreate() {
        super.onCreate()
        MemeDatabase.getDatabase(this)
    }
}