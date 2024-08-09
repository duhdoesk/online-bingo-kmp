package domain.user.use_case

import domain.theme.use_case.GetAllThemesUseCase
import domain.theme.use_case.GetCharactersByThemeId
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

private val THEME_IDS = setOf("1", "2", "5", "6", "8")

class GetProfilePicturesUseCase(
    private val getAllThemesUseCase: GetAllThemesUseCase,
    private val getCharactersByThemeId: GetCharactersByThemeId,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<ProfilePictures> {
        return getAllThemesUseCase().flatMapConcat { themes ->
            val flows = themes
                .filter { it.id in THEME_IDS }
                .map { theme ->
                getCharactersByThemeId(theme.id)
                    .map { characters ->
                        ProfilePictures.Category(
                            name = theme.name,
                            pictures = characters.map { it.pictureUri }
                        )
                    }
            }

            combine(flows) { it.toList() }
                .map { ProfilePictures(it) }
        }
    }
}

data class ProfilePictures(
    val categories: List<Category>
) {
    data class Category(
        val name: String,
        val pictures: List<String>
    )
}