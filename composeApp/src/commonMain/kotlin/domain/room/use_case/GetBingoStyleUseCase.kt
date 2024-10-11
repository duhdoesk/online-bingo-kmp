package domain.room.use_case

import domain.room.model.BingoType
import domain.theme.use_case.GetThemeByIdUseCase
import domain.theme.use_case.GetThemeCharactersUseCase
import ui.presentation.room.state.auxiliar.BingoStyle

class GetBingoStyleUseCase(
    private val getThemeByIdUseCase: GetThemeByIdUseCase,
    private val getThemeCharactersUseCase: GetThemeCharactersUseCase,
) {
    suspend operator fun invoke(
        bingoType: BingoType,
        themeId: String?,
    ): Result<BingoStyle> {
        when (bingoType) {
            BingoType.CLASSIC -> return Result.success(BingoStyle.Classic)
            BingoType.THEMED -> {
                val theme =
                    themeId?.let { getThemeByIdUseCase(it) }?.getOrNull()

                val characters =
                    themeId?.let { getThemeCharactersUseCase(it) }?.getOrNull()

                if (theme == null || characters == null) {
                    return Result.failure(Exception())
                }

                return Result.success(
                    BingoStyle.Themed(
                        theme = theme,
                        characters = characters,
                    )
                )
            }
        }
    }
}