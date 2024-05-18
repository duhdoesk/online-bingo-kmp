package domain.theme.repository

import kotlinx.coroutines.flow.Flow
import domain.theme.model.BingoTheme

interface BingoThemeRepository {
    fun getThemeById(id: String): Flow<BingoTheme>
    fun getAllThemes(): Flow<List<BingoTheme>>
}