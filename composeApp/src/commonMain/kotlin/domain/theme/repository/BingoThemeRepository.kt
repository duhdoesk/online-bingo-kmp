package domain.theme.repository

import data.theme.model.BingoThemeDTO
import kotlinx.coroutines.flow.Flow

interface BingoThemeRepository {
    suspend fun getThemeById(id: String): Result<BingoThemeDTO>
    fun flowThemeById(id: String): Flow<BingoThemeDTO>
    fun getAllThemes(): Flow<List<BingoThemeDTO>>
    fun observeAvailableThemes(): Flow<List<BingoThemeDTO>>
}
