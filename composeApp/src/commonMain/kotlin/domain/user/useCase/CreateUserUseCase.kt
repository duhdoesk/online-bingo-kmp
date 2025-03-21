package domain.user.useCase

import domain.user.repository.UserRepository

class CreateUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(
        id: String,
        email: String,
        name: String,
        pictureUri: String,
        victoryMessage: String
    ): Result<Unit> {
        return userRepository.createUser(
            id = id,
            email = email,
            name = name,
            pictureUri = pictureUri,
            victoryMessage = victoryMessage
        )
    }
}
