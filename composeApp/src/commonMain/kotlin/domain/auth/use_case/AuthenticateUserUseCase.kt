package domain.auth.use_case

import domain.auth.AuthService
import domain.user.repository.UserRepository

class AuthenticateUserUseCase(
    private val authService: AuthService,
    private val userRepository: UserRepository,
) {

    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        try {
            val firebaseUser = authService
                .authenticate(email = email, password = password)
                .user

            firebaseUser?.let { fu ->
                val user = userRepository.getUserById(fu.uid)
                val noName = (user.name == "Bingo Friend")
                val noEmail = (user.email == "")

                if (noName && noEmail) {
                    userRepository
                        .createUser(
                            id = fu.uid,
                            email = fu.email ?: "",
                            name = fu.displayName ?: ""
                        )
                }
            }

            return Result.success(Unit)
        } catch(e: Exception) {
            println(e.cause)
            println(e.message)

            return Result.failure(e)
        }
    }
}