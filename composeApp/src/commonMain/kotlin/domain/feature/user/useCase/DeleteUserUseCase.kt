package domain.feature.user.useCase

import domain.feature.user.UserRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class DeleteUserUseCase(private val userRepository: UserRepository) {
    operator fun invoke(id: String): Flow<Resource<Unit>> {
        return userRepository.deleteUser(id)
    }
}
