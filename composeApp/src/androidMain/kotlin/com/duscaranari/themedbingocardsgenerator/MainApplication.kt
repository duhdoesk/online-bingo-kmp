package com.duscaranari.themedbingocardsgenerator

import android.app.Application
import com.google.firebase.ktx.app
import com.google.firebase.ktx.initialize
import data.di.initKoin
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.app

class MainApplication: Application() {
    override fun onCreate() {
        super.onCreate()
//        startKoin{
//            androidContext(this@MainApplication)
//            modules(dataModule())
//        }
        initKoin()
    }
}