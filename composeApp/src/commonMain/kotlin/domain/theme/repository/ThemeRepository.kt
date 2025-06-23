package domain.theme.repository

import domain.theme.model.Theme
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

interface ThemeRepository {

    /** Returns a theme given it's ID */
    fun getThemeById(id: String): Flow<Resource<Theme>>

    /** Returns all themes */
    fun getAllThemes(): Flow<Resource<List<Theme>>>

    /** Returns all available themes */
    fun getAvailableThemes(): Flow<Resource<List<Theme>>>

    /** Creates a new theme and returns its ID */
    fun uploadNewTheme(
        name: String,
        nameEnglish: String,
        nameSpanish: String,
        picture: String,
        available: Boolean = false
    ): Flow<Resource<String>>
}
