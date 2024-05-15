package ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Configuration {
    @Serializable
    data object HomeScreen: Configuration()
    @Serializable
    data object ThemesScreen: Configuration()
    @Serializable
    data object CreateScreen: Configuration()
    @Serializable
    data object JoinScreen: Configuration()
    @Serializable
    data object HostScreen: Configuration()
    @Serializable
    data object PlayScreen: Configuration()
    @Serializable
    data object ProfileScreen: Configuration()
}