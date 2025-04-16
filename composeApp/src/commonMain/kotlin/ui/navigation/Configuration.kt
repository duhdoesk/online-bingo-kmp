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
    data class HostScreenThemed(val roomId: String) : Configuration()

    @Serializable
    data class HostScreenClassic(val roomId: String) : Configuration()

    @Serializable
    data class PlayerScreenThemed(val roomId: String) : Configuration()

    @Serializable
    data class PlayerScreenClassic(val roomId: String) : Configuration()

    @Serializable
    data object ProfileScreen : Configuration()

    @Serializable
    data object SignInScreen : Configuration()

    @Serializable
    data object SplashScreen : Configuration()

    @Serializable
    data object ForgotPasswordScreen : Configuration()

    @Serializable
    data object ChangePasswordScreen : Configuration()

    @Serializable
    data object PaywallScreen : Configuration()

    @Serializable
    data object CreateUserScreen : Configuration()
}
