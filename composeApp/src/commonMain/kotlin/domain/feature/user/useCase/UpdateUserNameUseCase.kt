package domain.feature.user.useCase

import domain.feature.user.UserRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class UpdateUserNameUseCase(private val userRepository: UserRepository) {

    operator fun invoke(newName: String): Flow<Resource<Unit>> {
        return userRepository.updateUserName(name = newName)
    }
}
