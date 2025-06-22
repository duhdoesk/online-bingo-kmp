package domain.feature.user.useCase

import domain.room.repository.BingoRoomRepository

class GetRoomPlayersUseCase(private val roomRepository: BingoRoomRepository) {

//    operator fun invoke(roomId: String): Flow<Resource<List<User>>> {
//        return roomRepository.getRoomById(roomId)
//            .map { resource ->
//                when (resource) {
//                    is Resource.Failure -> {
//                        Resource.Failure(resource.cause)
//                    }
//
//                    is Resource.Success -> {
//                        Resource.Success(resource.data.players)
//                    }
//                }
//            }
//    }
}
