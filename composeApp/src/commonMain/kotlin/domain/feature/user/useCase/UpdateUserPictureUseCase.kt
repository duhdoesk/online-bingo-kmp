package domain.feature.user.useCase

import domain.feature.user.UserRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class UpdateUserPictureUseCase(private val userRepository: UserRepository) {

    operator fun invoke(userId: String, pictureUri: String): Flow<Resource<Unit>> {
        return userRepository.updateUserPictureUri(id = userId, pictureUri = pictureUri)
    }
}
