package domain.feature.user.useCase

import domain.feature.user.UserRepository
import domain.feature.user.model.User
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class GetUserByIdUseCase(private val userRepository: UserRepository) {

    operator fun invoke(id: String): Flow<Resource<User>> {
        return userRepository.getUserById(id)
    }
}
