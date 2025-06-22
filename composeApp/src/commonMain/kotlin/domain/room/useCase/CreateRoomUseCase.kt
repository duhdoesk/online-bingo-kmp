package domain.room.useCase

import domain.room.model.BingoType
import domain.room.model.RoomPrivacy
import domain.room.repository.BingoRoomRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class CreateRoomUseCase(private val roomRepository: BingoRoomRepository) {
    operator fun invoke(
        hostId: String,
        name: String,
        privacy: RoomPrivacy,
        maxWinners: Int,
        type: BingoType,
        themeId: String?
    ): Flow<Resource<String>> {
        return roomRepository.createRoom(
            hostId = hostId,
            name = name,
            privacy = privacy,
            maxWinners = maxWinners,
            type = type,
            themeId = themeId
        )
    }
}
