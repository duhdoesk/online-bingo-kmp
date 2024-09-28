package domain.user.use_case

import domain.user.repository.UserRepository
import io.github.jan.supabase.gotrue.user.UserInfo

class CheckIfIsNewUserUseCase(private val userRepository: UserRepository) {
    suspend operator fun invoke(userInfo: UserInfo) {
        userRepository.checkIfUserExists(userInfo.id)
            .onSuccess { result ->
                if (!result) {
                    userRepository
                        .createUser(
                            id = userInfo.id,
                            email = userInfo.email.orEmpty(),
                            name = "Bingo Friend",
                        )
                }
            }
    }
}