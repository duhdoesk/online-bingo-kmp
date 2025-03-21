package domain.room.useCase

import domain.room.repository.BingoRoomRepository

class JoinRoomUseCase(private val roomRepository: BingoRoomRepository) {

    suspend operator fun invoke(
        roomId: String,
        userId: String,
        roomPassword: String?
    ): Result<Unit> {
        roomRepository.getRoomById(roomId)
            .onSuccess { room ->
                if (room.locked && room.password != roomPassword) {
                    return Result.failure(JoinRoomException(message = "Incorrect Password"))
                }

                return roomRepository.joinRoom(roomId, userId)
            }
            .onFailure { exception -> return Result.failure(exception) }

        return Result.failure(JoinRoomException("No Response"))
    }
}

class JoinRoomException(message: String) : Exception(message)
