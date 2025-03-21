package domain.user.useCase

import domain.user.repository.UserRepository
import io.github.jan.supabase.gotrue.user.UserInfo

class CheckIfIsNewUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(userInfo: UserInfo): Result<Boolean> {
        userRepository.checkIfUserExists(userInfo.id).fold(
            onSuccess = { return Result.success(it) },
            onFailure = { return Result.failure(it) }
        )
    }

//    suspend operator fun invoke(userInfo: UserInfo) {
//        userRepository.checkIfUserExists(userInfo.id)
//            .onSuccess { result ->
//                if (!result) {
//                    userRepository
//                        .createUser(
//                            id = userInfo.id,
//                            email = userInfo.email.orEmpty(),
//                            name = "Bingo Friend",
//                        )
//                }
//            }
//    }
}
