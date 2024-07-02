package ui.presentation.sign_in

import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseAuthException
import dev.gitlive.firebase.auth.FirebaseAuthInvalidCredentialsException
import dev.gitlive.firebase.auth.FirebaseAuthUserCollisionException
import domain.auth.AuthService
import domain.user.model.User
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.componentCoroutineScope

class SignInScreenComponent(
    componentContext: ComponentContext,
    user: User?,
    private val onSignIn: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val authService by inject<AuthService>()

    init {
        if (user != null) onSignIn()
    }

    fun signIn(email: String, password: String) =
        componentCoroutineScope().launch {
            try {
                authService
                    .authenticate(email, password)
                    .user?.let { onSignIn() }
            } catch (e: Exception) {
                println(e.cause)
                println(e.message)
            }
        }

    fun createUser(email: String, password: String) =
        componentCoroutineScope().launch {
            try {
                authService
                    .createUser(email, password)
                    .user?.let { onSignIn() }
            } catch (e: Exception) {
                println(e.cause)
                println(e.message)
            }
        }

    fun sendPasswordResetEmail(email: String) =
        componentCoroutineScope().launch {
            try {
                authService
                    .sendPasswordResetEmail(email)
            } catch (e: Exception) {
                println(e.cause)
                println(e.message)
            }
        }
}