package ui.presentation.sign_up

import com.arkivanov.decompose.ComponentContext
import domain.auth.AuthService
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.componentCoroutineScope

class SignUpScreenComponent(
    componentContext: ComponentContext,
    private val onSignUp: () -> Unit,
    private val onPopBack: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val authService by inject<AuthService>()

    fun signUp(email: String, password: String) =
        componentCoroutineScope().launch {
            try {
                authService
                    .createUser(email, password)
                    .user?.let { onSignUp() }
            } catch (e: Exception) {
                println(e.cause)
                println(e.message)
            }
        }

    fun popBack() = onPopBack()
}