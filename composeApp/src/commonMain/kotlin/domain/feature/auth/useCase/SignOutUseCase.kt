package domain.feature.auth.useCase

import domain.feature.auth.AuthRepository
import domain.util.resource.Resource
import kotlinx.coroutines.flow.Flow

class SignOutUseCase(private val authRepository: AuthRepository) {

    operator fun invoke(): Flow<Resource<Unit>> {
        return authRepository.signOut()
    }
}
