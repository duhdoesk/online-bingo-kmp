package ui.navigation

import domain.room.model.BingoType
import kotlinx.serialization.Serializable

@Serializable
sealed class Configuration {
    @Serializable
    data object HomeScreen : Configuration()

    @Serializable
    data object ThemesScreen : Configuration()

    @Serializable
    data class CreateScreen(val bingoType: BingoType) : Configuration()

    @Serializable
    data class JoinScreen(val bingoType: BingoType) : Configuration()

    @Serializable
    data class HostScreen(val roomId: String) : Configuration()

    @Serializable
    data class PlayScreen(val roomId: String) : Configuration()

    @Serializable
    data object ProfileScreen : Configuration()

    @Serializable
    data object SignInScreen : Configuration()

    @Serializable
    data object SignUpScreen : Configuration()

    @Serializable
    data object ForgotPasswordScreen : Configuration()

    @Serializable
    data object EditProfilePictureScreen : Configuration()

    @Serializable
    data object ChangePasswordScreen : Configuration()

    @Serializable
    data class ClassicHostScreen(val roomId: String) : Configuration()

    @Serializable
    data class ClassicPlayScreen(val roomId: String) : Configuration()
}