package domain.auth.use_case

import domain.auth.AuthService

class SignOutUseCase(
    private val authService: AuthService,
) {
    suspend operator fun invoke(): Result<Unit> =
        authService.signOut()
}