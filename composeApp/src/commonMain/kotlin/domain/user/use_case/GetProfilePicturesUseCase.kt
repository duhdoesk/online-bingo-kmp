package domain.user.use_case

import domain.theme.use_case.GetCharactersByThemeId
import domain.theme.use_case.ObserveAvailableThemes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class GetProfilePicturesUseCase(
    private val observeAvailableThemes: ObserveAvailableThemes,
    private val getCharactersByThemeId: GetCharactersByThemeId,
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<ProfilePictures> {
        return observeAvailableThemes().flatMapConcat { themes ->
            val flows = themes
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