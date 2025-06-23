package data.profilePictures

import domain.character.repository.CharacterRepository
import domain.profilePictures.ProfilePicturesRepository
import domain.profilePictures.model.ProfilePictures
import domain.theme.repository.ThemeRepository
import domain.util.resource.Cause
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take

class ProfilePicturesRepositoryImpl(
    private val themeRepository: ThemeRepository,
    private val characterRepository: CharacterRepository
) : ProfilePicturesRepository {

    override fun getProfilePictures(): Flow<Resource<ProfilePictures>> {
        return themeRepository.getAvailableThemes()
            .take(1)
            .map { themesRes ->
                val themes = themesRes.getOrNull()
                    ?: return@map Resource.Failure(Cause.INVALID_THEME)

                val categories = themes
                    .map { theme ->
                        val characters = characterRepository
                            .getThemeCharacters(theme.id).first().getOrNull()
                            ?.map { it.pictureUri }
                            ?: emptyList()

                        ProfilePictures.Category(
                            name = theme.name,
                            pictures = characters
                        )
                    }

                Resource.Success(ProfilePictures(categories))
            }
    }
}
