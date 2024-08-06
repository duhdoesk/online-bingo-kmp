package domain.auth.use_case

import dev.gitlive.firebase.auth.EmailAuthProvider
import domain.auth.AuthService

class ChangePasswordWithReAuthenticationUseCase(
    private val authService: AuthService,
) {
    suspend operator fun invoke(newPassword: String, currentPassword: String): Result<Unit> {

        val credential = EmailAuthProvider.credential(
            email = authService.currentUser?.email ?: "",
            password = currentPassword
        )

        authService.reAuthenticateUser(credential)
            .onFailure { return Result.failure(it) }

        return authService.updatePassword(newPassword)
    }
}