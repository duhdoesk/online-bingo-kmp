package com.duscaranari.themedbingocardsgenerator

import android.app.Application
import data.di.initKoin
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
//        startKoin{
//            androidContext(this@MainApplication)
//            modules(dataModule())
//        }
        initKoin()
    }
}