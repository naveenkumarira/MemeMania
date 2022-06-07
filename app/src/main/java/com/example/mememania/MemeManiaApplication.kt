package com.example.mememania

import android.app.Application
import com.example.mememania.data.local.MemeDatabase
import com.example.mememania.data.network.NetworkHandler
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MemeManiaApplication() : Application() {
    @Inject
    lateinit var networkHandler: NetworkHandler

    override fun onCreate() {
        super.onCreate()
        networkHandler.init(null)
        MemeDatabase.getDatabase(this)
    }
}