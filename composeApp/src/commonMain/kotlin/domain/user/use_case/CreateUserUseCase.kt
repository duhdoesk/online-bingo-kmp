package domain.user.use_case

import domain.user.repository.UserRepository

class CreateUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(id: String, email: String): Result<Unit> {
        return userRepository.createUser(
            id = id,
            email = email,
            name = "Amigo Temático",
        )
    }
}