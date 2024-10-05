package domain.theme.use_case

import domain.room.repository.BingoRoomRepository
import domain.theme.model.BingoTheme
import domain.theme.repository.BingoThemeRepository

class GetRoomThemeUseCase(
    private val roomRepository: BingoRoomRepository,
    private val themeRepository: BingoThemeRepository,
) {
    suspend operator fun invoke(roomId: String): Result<BingoTheme> {
        roomRepository.getRoomById(roomId).fold(
            onFailure = { exception -> return Result.failure(exception) },
            onSuccess = { room ->
                themeRepository.getThemeById(room.themeId!!).fold(
                    onFailure = { exception -> return Result.failure(exception) },
                    onSuccess = { themeDTO -> return Result.success(themeDTO.toModel()) },
                )
            }
        )
    }
}