package ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Configuration {
    @Serializable
    data object HomeScreen: Configuration()
    @Serializable
    data object ThemesScreen: Configuration()
}