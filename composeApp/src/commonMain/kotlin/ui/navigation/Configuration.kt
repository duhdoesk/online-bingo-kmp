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
    data class HostScreen(val roomId: String): Configuration()
    @Serializable
    data class PlayScreen(val roomId: String): Configuration()
    @Serializable
    data object ProfileScreen: Configuration()
    @Serializable
    data object SignInScreen: Configuration()
    @Serializable
    data object SignUpScreen: Configuration()
    @Serializable
    data object ForgotPasswordScreen: Configuration()
    @Serializable
    data object EditProfilePictureScreen: Configuration()
    @Serializable
    data object ChangePasswordScreen: Configuration()
}