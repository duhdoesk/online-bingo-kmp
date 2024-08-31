package domain.auth.supabase.use_case

import domain.auth.supabase.SupabaseAuthService
import domain.user.repository.UserRepository
import io.github.jan.supabase.gotrue.SessionStatus
import io.github.jan.supabase.gotrue.auth

class SignInWithGoogleUseCase(
    private val supabaseAuthService: SupabaseAuthService,
    private val userRepository: UserRepository,
) {
    suspend operator fun invoke() {
        supabaseAuthService.signInWithGoogle()

        val sessionStatus = supabaseAuthService
            .supabaseClient
            .auth
            .sessionStatus
            .value

        if (sessionStatus is SessionStatus.Authenticated && sessionStatus.isNew) {
            userRepository.createUser(
                id = sessionStatus.session.user?.id.orEmpty(),
                email = sessionStatus.session.user?.email.orEmpty(),
                name = "Amigo Temático",
            )
        }
    }
}