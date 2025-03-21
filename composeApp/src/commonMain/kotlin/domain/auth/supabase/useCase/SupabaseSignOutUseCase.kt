package domain.auth.supabase.useCase

import domain.auth.supabase.SupabaseAuthService

class SupabaseSignOutUseCase(private val supabaseAuthService: SupabaseAuthService) {
    suspend operator fun invoke(): Result<Unit> {
        return supabaseAuthService.signOut()
    }
}
