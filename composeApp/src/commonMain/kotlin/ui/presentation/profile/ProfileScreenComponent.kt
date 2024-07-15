package ui.presentation.profile

import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseUser
import domain.auth.AuthService
import domain.user.model.User
import domain.user.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.componentCoroutineScope

class ProfileScreenComponent(
    componentContext: ComponentContext,
    val firebaseUser: FirebaseUser,
    private val onPopBack: () -> Unit,
    private val onSignOut: () -> Unit
): ComponentContext by componentContext, KoinComponent {

    private val authService by inject<AuthService>()
    private val userRepository by inject<UserRepository>()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    init {
        componentCoroutineScope().launch {
            _user.update { userRepository.getUserById(firebaseUser.uid) }
        }
    }

    fun popBack() = onPopBack()

    fun signOut() =
        componentCoroutineScope().launch {
            authService.signOut()
            onSignOut()
        }
}