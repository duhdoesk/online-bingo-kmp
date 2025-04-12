package ui.presentation.signIn

import com.arkivanov.decompose.ComponentContext
import io.github.jan.supabase.SupabaseClient
import org.koin.core.component.KoinComponent

class SignInScreenComponent(
    componentContext: ComponentContext,
    val supabaseClient: SupabaseClient,
    private val onSignIn: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    /**
     * Function to navigate when sign in is successful
     */
    fun signIn() {
        onSignIn()
    }
}
