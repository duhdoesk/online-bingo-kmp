package domain.user.use_case

import domain.user.model.User
import domain.user.repository.UserRepository
import kotlinx.coroutines.flow.Flow

class FlowUserUseCase(
    private val userRepository: UserRepository
) {
    operator fun invoke(id: String): Flow<User?> =
        userRepository.flowUser(id)
}