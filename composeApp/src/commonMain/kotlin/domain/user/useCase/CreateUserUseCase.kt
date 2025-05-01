package domain.user.useCase

import domain.user.repository.UserRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class CreateUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(
        id: String,
        email: String,
        name: String,
        pictureUri: String,
        victoryMessage: String
    ): Flow<Resource<Unit>> {
        return userRepository.createUser(
            id = id,
            email = email,
            name = name,
            pictureUri = pictureUri,
            message = victoryMessage
        )
    }
}
