package domain.user.use_case

import domain.user.repository.UserRepository

class UpdateVictoryMessageUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(userId: String, newVictoryMessage: String) =
        userRepository.updateVictoryMessage(id = userId, victoryMessage = newVictoryMessage)
}