package ui.presentation.profile

import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseUser
import domain.auth.use_case.DeleteAccountUseCase
import domain.auth.use_case.SignOutUseCase
import domain.auth.use_case.UpdatePasswordUseCase
import domain.user.model.User
import domain.user.use_case.GetUserByIdUseCase
import domain.user.use_case.UpdateNameUseCase
import domain.user.use_case.UpdateVictoryMessageUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.presentation.profile.state.ProfileScreenUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import util.componentCoroutineScope

class ProfileScreenComponent(
    componentContext: ComponentContext,
    val firebaseUser: FirebaseUser,
    private val onPopBack: () -> Unit,
    private val onSignOut: () -> Unit,
    private val onUpdatePicture: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

    private val signOutUseCase by inject<SignOutUseCase>()
    private val getUserByIdUseCase by inject<GetUserByIdUseCase>()
    private val deleteAccountUseCase by inject<DeleteAccountUseCase>()
    private val updatePasswordUseCase by inject<UpdatePasswordUseCase>()
    private val updateNameUseCase by inject<UpdateNameUseCase>()
    private val updateVictoryMessageUseCase by inject<UpdateVictoryMessageUseCase>()

    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    @OptIn(ExperimentalResourceApi::class)
    val successDialog = mutableDialogStateOf<StringResource?>(null)

    @OptIn(ExperimentalResourceApi::class)
    val errorDialog = mutableDialogStateOf<StringResource?>(null)

    val updateNameDialog = mutableDialogStateOf(null)
    val updateVictoryMessageDialog = mutableDialogStateOf(null)
    val updatePasswordDialog = mutableDialogStateOf(null)

    init {
        getUserById()
    }

    fun popBack() {
        onPopBack()
    }

    fun signOut() =
        componentCoroutineScope().launch {
            signOutUseCase.invoke()
                .onSuccess {
                    onSignOut()
                }
                .onFailure {
                    //todo(): display dialog showing the error message
                }
        }

    fun deleteAccount() {
        componentCoroutineScope().launch {
            deleteAccountUseCase.invoke()
                .onSuccess {
                    //todo(): display dialog showing success message and then go to sign in screen
                }
                .onFailure {
                    //todo(): display dialog showing the error message
                }
        }
    }

    fun updatePicture() {
        onUpdatePicture()
    }

    fun updatePassword(newPassword: String, currentPassword: String) {
        componentCoroutineScope().launch {
            updatePasswordUseCase
                .invoke(
                    newPassword = newPassword,
                    currentPassword = currentPassword
                )
                .onSuccess {
                    //todo(): display dialog showing the success message
                }
                .onFailure {
                    //todo(): display dialog showing the error message
                }
        }
    }

    fun updateName(newName: String) {
        componentCoroutineScope().launch {
            updateNameUseCase.invoke(
                userId = firebaseUser.uid,
                newName = newName
            )
                .onSuccess {
                    //todo(): display dialog showing the success message
                }
                .onFailure {
                    //todo(): display dialog showing the error message
                }
        }
    }

    fun updateVictoryMessage(newVictoryMessage: String) {
        componentCoroutineScope().launch {
            updateVictoryMessageUseCase.invoke(
                userId = firebaseUser.uid,
                newVictoryMessage = newVictoryMessage
            )
                .onSuccess {
                    //todo(): display dialog showing the success message
                }
                .onFailure {
                    //todo(): display dialog showing the error message
                }
        }
    }

    private fun getUserById() {
        componentCoroutineScope().launch {
            _user.update { getUserByIdUseCase.invoke(firebaseUser.uid) }
        }
    }
}