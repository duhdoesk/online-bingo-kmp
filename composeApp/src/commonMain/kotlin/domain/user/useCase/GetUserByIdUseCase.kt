package domain.user.useCase

import domain.user.model.User
import domain.user.repository.UserRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class GetUserByIdUseCase(private val userRepository: UserRepository) {
    operator fun invoke(id: String): Flow<Resource<User>> {
        return userRepository.getUserById(id)
    }
}
