package domain.auth.use_case

import domain.auth.AuthService
import domain.user.repository.UserRepository

class CreateUserUseCase(
    private val authService: AuthService,
    private val userRepository: UserRepository
) {
    suspend operator fun invoke(
        email: String,
        password: String
    ): Result<Unit> {
        try {
            val firebaseUser = authService
                .createUser(email, password)
                .user

            firebaseUser?.let { user ->
                userRepository.createUser(
                    id = user.uid,
                    email = user.email ?: "",
                    name = user.displayName ?: "",
                )
            }

            return Result.success(Unit)

        } catch (e: Exception) {
            println(e.cause)
            println(e.message)

            return Result.failure(e)
        }
    }
}