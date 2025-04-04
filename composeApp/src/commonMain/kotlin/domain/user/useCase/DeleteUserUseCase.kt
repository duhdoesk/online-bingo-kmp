package domain.user.useCase

import domain.user.repository.UserRepository

class DeleteUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: String): Result<Unit> {
        return userRepository.deleteUser(id)
    }
}
