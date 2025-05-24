package domain.feature.user.useCase

import domain.feature.user.UserRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class UpdateUserPictureUseCase(private val userRepository: UserRepository) {

    operator fun invoke(pictureUri: String): Flow<Resource<Unit>> {
        return userRepository.updateUserPictureUri(pictureUri = pictureUri)
    }
}
