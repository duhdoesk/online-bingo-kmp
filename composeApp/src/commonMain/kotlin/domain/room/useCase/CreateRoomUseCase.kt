package domain.room.useCase

import domain.room.model.BingoType
import domain.room.repository.BingoRoomRepository

class CreateRoomUseCase(private val roomRepository: BingoRoomRepository) {
    suspend operator fun invoke(
        hostId: String,
        name: String,
        locked: Boolean,
        password: String?,
        maxWinners: Int,
        type: BingoType,
        themeId: String?
    ): Result<String> {
        return roomRepository.createRoom(
            hostId = hostId,
            name = name,
            locked = locked,
            password = password,
            maxWinners = maxWinners,
            type = type,
            themeId = themeId
        )
    }
}
