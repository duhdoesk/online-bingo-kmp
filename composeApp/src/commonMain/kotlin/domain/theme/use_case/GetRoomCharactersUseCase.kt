package domain.theme.use_case

import domain.character.model.Character
import domain.room.repository.BingoRoomRepository
import domain.theme.repository.BingoThemeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest

class GetRoomCharactersUseCase(
    private val roomRepository: BingoRoomRepository,
    private val themeRepository: BingoThemeRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(roomId: String): Flow<List<Character>> {
        return roomRepository.flowRoomById(roomId).flatMapLatest { room ->
            themeRepository.getCharacters(room.themeId!!)
        }
    }
}