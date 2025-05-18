package domain.feature.user.useCase

import domain.feature.user.UserRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class UpdateUserVictoryMessageUseCase(private val userRepository: UserRepository) {

    operator fun invoke(newVictoryMessage: String): Flow<Resource<Unit>> {
        return userRepository.updateVictoryMessage(victoryMessage = newVictoryMessage)
    }
}
