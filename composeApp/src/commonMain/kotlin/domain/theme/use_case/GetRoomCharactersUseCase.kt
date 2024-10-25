package domain.theme.use_case

import domain.character.model.Character
import domain.room.repository.BingoRoomRepository
import domain.theme.repository.BingoThemeRepository

class GetRoomCharactersUseCase(
    private val roomRepository: BingoRoomRepository,
    private val themeRepository: BingoThemeRepository,
) {
    suspend operator fun invoke(roomId: String): Result<List<Character>> {
        roomRepository.getRoomById(roomId).fold(
            onFailure = { exception ->
                return Result.failure(exception)
            },
            onSuccess = { room ->
                themeRepository.getThemeCharacters(room.themeId!!).fold(
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