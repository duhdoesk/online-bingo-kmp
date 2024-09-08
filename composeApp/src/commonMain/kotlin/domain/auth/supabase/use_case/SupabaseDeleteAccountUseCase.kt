package domain.auth.supabase.use_case

import domain.auth.supabase.SupabaseAuthService

class SupabaseDeleteAccountUseCase(private val supabaseAuthService: SupabaseAuthService) {
    suspend operator fun invoke(uid: String): Result<Unit> {
        return supabaseAuthService.deleteAccount(uid)
    }
}