package domain.theme.repository

import kotlinx.coroutines.flow.Flow
import domain.theme.model.BingoTheme

interface BingoThemeRepository {
    suspend fun getThemeById(id: String): BingoTheme
    suspend fun getAllThemes(): List<BingoTheme>
}