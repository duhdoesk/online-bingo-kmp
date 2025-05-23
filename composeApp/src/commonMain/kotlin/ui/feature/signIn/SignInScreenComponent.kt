package ui.feature.signIn

import com.arkivanov.decompose.ComponentContext
import domain.feature.auth.useCase.GetSessionStatusUseCase
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.componentCoroutineScope

@OptIn(ExperimentalResourceApi::class)
class SignInScreenComponent(
    componentContext: ComponentContext,
    private val onSignIn: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()
    private val getSessionStatusUseCase: GetSessionStatusUseCase by inject()
    val supabaseClient: SupabaseClient by inject()

    init {
        coroutineScope.launch {
            getSessionStatusUseCase().collect { resource ->
                if (resource.getOrNull() is SessionStatus.Authenticated) {
                    onSignIn()
                }
            }
        }
    }

    /** Fetches user id then navigates passing it */
    fun signIn() {
        onSignIn()
    }
}
