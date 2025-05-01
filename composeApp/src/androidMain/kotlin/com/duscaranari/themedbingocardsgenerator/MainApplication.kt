package com.duscaranari.themedbingocardsgenerator

import android.app.Application
import data.di.dataModule
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize
import domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import ui.di.uiModule

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Firebase.initialize(this)
        startKoin {
            androidContext(this@MainApplication)
            modules(
                dataModule,
                domainModule,
                uiModule
            )
        }
    }
}
