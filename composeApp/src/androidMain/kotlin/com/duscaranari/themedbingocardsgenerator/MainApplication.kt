package com.duscaranari.themedbingocardsgenerator

import android.app.Application
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import di.initKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
        initKoin()
    }
}