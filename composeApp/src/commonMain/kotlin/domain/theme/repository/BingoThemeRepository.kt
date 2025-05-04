package domain.theme.repository

import data.theme.model.BingoThemeDTO
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

interface BingoThemeRepository {
    suspend fun getThemeById(id: String): Result<BingoThemeDTO>
    fun flowThemeById(id: String): Flow<BingoThemeDTO>
    fun getAllThemes(): Flow<List<BingoThemeDTO>>
    fun observeAvailableThemes(): Flow<List<BingoThemeDTO>>

    fun uploadNewTheme(
        name: String,
        nameEnglish: String,
        nameSpanish: String,
        picture: String,
        available: Boolean = false
    ): Flow<Resource<String>>
}
