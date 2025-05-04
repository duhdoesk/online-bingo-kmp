package domain.feature.auth.useCase

import domain.feature.auth.AuthRepository
import domain.util.resource.Resource
import io.github.jan.supabase.auth.user.UserInfo
import kotlinx.coroutines.flow.Flow

class GetSessionInfoUseCase(private val authRepository: AuthRepository) {

    operator fun invoke(): Flow<Resource<UserInfo>> {
        return authRepository.getSessionInfo()
    }
}
