package domain.user.useCase

import domain.character.useCase.ObserveThemeCharacters
import domain.profilePictures.ProfilePictures
import domain.theme.useCase.ObserveAvailableThemes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class GetProfilePicturesUseCase(
    private val observeAvailableThemes: ObserveAvailableThemes,
    private val observeThemeCharacters: ObserveThemeCharacters
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    operator fun invoke(): Flow<ProfilePictures> {
        return observeAvailableThemes().flatMapConcat { themes ->
            val flows = themes
                .map { theme ->
                    observeThemeCharacters(theme.id)
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
