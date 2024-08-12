package domain.theme.use_case

import domain.room.repository.BingoRoomRepository
import domain.theme.model.BingoTheme
import domain.theme.repository.BingoThemeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow

class GetRoomThemeUseCase(
    private val roomRepository: BingoRoomRepository,
    private val themeRepository: BingoThemeRepository,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(roomId: String): Flow<BingoTheme> {
        return roomRepository.flowRoomById(roomId).flatMapLatest { room ->
            themeRepository.flowThemeById(room.themeId!!)
        }
    }
}