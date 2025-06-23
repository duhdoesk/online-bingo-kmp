package domain.feature.user.useCase

import domain.profilePictures.ProfilePicturesRepository
import domain.profilePictures.model.ProfilePictures
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class GetProfilePicturesUseCase(private val repository: ProfilePicturesRepository) {

    operator fun invoke(): Flow<Resource<ProfilePictures>> {
        return repository.getProfilePictures()
    }
}
