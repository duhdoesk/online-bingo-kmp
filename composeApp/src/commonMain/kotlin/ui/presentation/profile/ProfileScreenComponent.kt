package ui.presentation.profile

import com.arkivanov.decompose.ComponentContext
import domain.auth.AuthService
import domain.user.model.User
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.componentCoroutineScope

class ProfileScreenComponent(
    componentContext: ComponentContext,
    val user: User,
    private val onPopBack: () -> Unit,
    private val onSignOut: () -> Unit
): ComponentContext by componentContext, KoinComponent {

    private val authService by inject<AuthService>()

    fun popBack() = onPopBack()

    fun signOut() =
        componentCoroutineScope().launch {
            authService.signOut()
            onSignOut()
        }
}