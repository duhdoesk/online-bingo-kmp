package domain.user.useCase

import domain.user.repository.UserRepository

class UpdateNameUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String, newName: String): Result<Unit> =
        userRepository.updateUserName(id = userId, name = newName)
}
