package domain.user.useCase

import domain.room.repository.BingoRoomRepository
import domain.user.model.User
import domain.user.repository.UserRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class GetRoomPlayersUseCase(
    private val userRepository: UserRepository,
    private val roomRepository: BingoRoomRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(roomId: String): Flow<List<User>> {
        return roomRepository.flowRoomById(roomId).flatMapLatest { room ->
            userRepository.getListOfUsers(room.players)
        }
    }
}
