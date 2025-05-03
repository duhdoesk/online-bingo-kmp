@file:OptIn(ExperimentalCoroutinesApi::class)

package domain.feature.user.useCase

import domain.feature.user.UserRepository
import domain.feature.user.model.User
import domain.room.repository.BingoRoomRepository
import domain.util.resource.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class GetRoomPlayersUseCase(
    private val userRepository: UserRepository,
    private val roomRepository: BingoRoomRepository
) {
    operator fun invoke(roomId: String): Flow<Resource<List<User>>> {
        return roomRepository
            .flowRoomById(roomId)
            .flatMapLatest { room ->
                userRepository.getListOfUsers(room.players)
            }
    }
}
