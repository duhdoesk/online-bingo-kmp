package domain.user.use_case

import domain.user.model.User
import domain.user.repository.UserRepository

class GetUserByIdUseCase(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(userId: String): Result<User?> {
        return userRepository.getUserById(userId)
    }
}