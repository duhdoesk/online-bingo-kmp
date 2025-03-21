package domain.character.useCase

import domain.character.model.Character
import domain.character.repository.CharacterRepository
import domain.room.repository.BingoRoomRepository

class GetRoomCharacters(
    private val roomRepository: BingoRoomRepository,
    private val characterRepository: CharacterRepository
) {
    suspend operator fun invoke(roomId: String): Result<List<Character>> {
        roomRepository.getRoomById(roomId).fold(
            onFailure = { exception ->
                return Result.failure(exception)
            },
            onSuccess = { room ->
                characterRepository.getThemeCharacters(room.themeId!!).fold(
                    onFailure = { exception ->
                        return Result.failure(exception)
                    },
                    onSuccess = { list ->
                        val model = list.map { dto -> dto.toModel() }
                        return Result.success(model)
                    }
                )
            }
        )
    }
}
