package com.duscaranari.themedbingocardsgenerator

import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.arkivanov.decompose.retainedComponent
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.navigation.RootComponent

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalResourceApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootComponent = retainedComponent { RootComponent(it) }
        enableEdgeToEdge()
        setContent {
            App(rootComponent)
        }
    }
}
