package domain.user.use_case

import domain.user.repository.UserRepository

class UpdateUserPictureUseCase(
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke(userId: String, pictureUri: String) =
        userRepository.updateUserPictureUri(id = userId, pictureUri = pictureUri)
}