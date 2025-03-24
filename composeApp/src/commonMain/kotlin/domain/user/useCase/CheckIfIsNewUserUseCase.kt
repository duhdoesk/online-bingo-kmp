package domain.user.useCase

import domain.user.repository.UserRepository
import io.github.jan.supabase.auth.user.UserInfo

class CheckIfIsNewUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(userInfo: UserInfo): Result<Boolean> {
        userRepository.checkIfUserExists(userInfo.id).fold(
            onSuccess = { return Result.success(it) },
            onFailure = { return Result.failure(it) }
        )
    }
}
