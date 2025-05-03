package domain.feature.auth.useCase

import domain.feature.auth.AuthRepository
import domain.util.resource.Resource
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow

class GetSessionStatusUseCase(private val authRepository: AuthRepository) {

    operator fun invoke(): Flow<Resource<SessionStatus>> {
        return authRepository.getSessionStatus()
    }
}
