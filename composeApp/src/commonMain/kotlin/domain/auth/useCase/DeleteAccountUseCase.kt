package domain.auth.useCase

import domain.auth.AuthService

class DeleteAccountUseCase(
    private val authService: AuthService
) {
    suspend operator fun invoke(): Result<Unit> =
        authService.deleteAccount()
}
