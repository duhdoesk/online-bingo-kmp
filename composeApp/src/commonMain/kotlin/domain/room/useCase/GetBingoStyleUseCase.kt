package domain.room.useCase

import domain.character.useCase.GetThemeCharacters
import domain.room.model.BingoType
import domain.theme.useCase.GetThemeByIdUseCase
import ui.presentation.room.state.auxiliar.BingoStyle

class GetBingoStyleUseCase(
    private val getThemeByIdUseCase: GetThemeByIdUseCase,
    private val getThemeCharacters: GetThemeCharacters
) {
    suspend operator fun invoke(
        bingoType: BingoType,
        themeId: String?
    ): Result<BingoStyle> {
        when (bingoType) {
            BingoType.CLASSIC -> return Result.success(BingoStyle.Classic)
            BingoType.THEMED -> {
                val theme =
                    themeId?.let { getThemeByIdUseCase(it) }?.getOrNull()

                val characters =
                    themeId?.let { getThemeCharacters(it) }?.getOrNull()

                if (theme == null || characters.isNullOrEmpty()) {
                    return Result.failure(Exception())
                }

                return Result.success(
                    BingoStyle.Themed(
                        theme = theme,
                        characters = characters
                    )
                )
            }
        }
    }
}
