package domain.user.useCase

import domain.user.model.User
import domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class ObserveUser(
    private val userRepository: UserRepository
) {
    operator fun invoke(id: String): Flow<User?> =
        userRepository.observeUser(id)
}
