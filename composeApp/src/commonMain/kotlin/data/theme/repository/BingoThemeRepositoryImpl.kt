package data.theme.repository

import domain.theme.model.BingoTheme
import domain.theme.model.mockBingoThemeList
import domain.theme.repository.BingoThemeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BingoThemeRepositoryImpl() : BingoThemeRepository {
    override fun getThemeById(id: String): Flow<BingoTheme> {
        return flowOf(mockBingoThemeList()[0])
    }

    override fun getAllThemes(): Flow<List<BingoTheme>> {
        return flowOf(mockBingoThemeList())
    }
}