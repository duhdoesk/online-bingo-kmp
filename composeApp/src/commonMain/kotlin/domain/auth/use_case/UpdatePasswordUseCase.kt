package domain.auth.use_case

import dev.gitlive.firebase.auth.EmailAuthProvider
import domain.auth.AuthService

class UpdatePasswordUseCase(
    private val authService: AuthService,
) {
    suspend operator fun invoke(newPassword: String, currentPassword: String): Result<Unit> {
        val credential = EmailAuthProvider.credential(
            email = authService.currentUser?.email ?: "",
            password = currentPassword
        )

        try {
            val result = authService.currentUser?.reauthenticateAndRetrieveData(credential)

            if (result != null) {
                try {
                    authService.currentUser?.updatePassword(newPassword)
                    return Result.success(Unit)

                } catch (e: Exception) {
                    return Result.failure(e)

                }
            } else {
                return Result.failure(Exception())

            }
        } catch (e: Exception) {
            return Result.failure(e)

        }
    }
}