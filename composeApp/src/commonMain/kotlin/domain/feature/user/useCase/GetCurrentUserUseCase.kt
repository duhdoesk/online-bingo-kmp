package domain.feature.user.useCase

import domain.feature.user.UserRepository
import domain.feature.user.model.User
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class GetCurrentUserUseCase(private val userRepository: UserRepository) {

    operator fun invoke(): Flow<Resource<User>> {
        return userRepository.getCurrentUser()
    }
}
