package ui.presentation.profile.picture

import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseUser
import domain.user.model.User
import domain.user.use_case.GetProfilePicturesUseCase
import domain.user.use_case.GetUserByIdUseCase
import domain.user.use_case.UpdateUserPictureUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.componentCoroutineScope

class EditProfilePictureScreenComponent(
    private val firebaseUser: FirebaseUser?,
    private val onPictureSaved: () -> Unit,
    private val onCancel: () -> Unit,
    componentContext: ComponentContext
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()

    private val _uiState = MutableStateFlow(EditProfilePictureUiState.INITIAL)
    val uiState: StateFlow<EditProfilePictureUiState>
        get() = _uiState.asStateFlow()

    private val getUserByIdUseCase by inject<GetUserByIdUseCase>()
    private val updateUserPictureUseCase by inject<UpdateUserPictureUseCase>()
    private val getProfilePicturesUseCase by inject<GetProfilePicturesUseCase>()

    fun onEvent(event: EditProfilePictureUiEvent) {
        when (event) {
            is EditProfilePictureUiEvent.OnUiLoaded -> onUiLoaded()
            is EditProfilePictureUiEvent.OnConfirm -> onEditConfirmed()
            is EditProfilePictureUiEvent.OnPictureSelected -> onPictureSelected(event.pictureUrl)
            is EditProfilePictureUiEvent.OnCancel -> onCancel()
        }
    }

    private fun onUiLoaded() {
        coroutineScope.launch {
            combine(
                getUserData(),
                getProfilePicturesUseCase()
            ) { userData, pictures ->
                val user = userData.getOrNull() ?: return@combine EditProfilePictureUiState.INITIAL // TODO: Handle error

                val currentPictureUrl = user.pictureUri.takeIf { it.isNotEmpty() }

                EditProfilePictureUiState(
                    loading = false,
                    userId = user.id,
                    userName = user.name,
                    currentPictureUrl = currentPictureUrl,
                    selectedPictureUrl = currentPictureUrl,
                    pictures = pictures
                )
            }.collect {
                _uiState.value = it
            }
        }
    }

    private fun onPictureSelected(pictureUrl: String) {
        _uiState.update { it.copy(selectedPictureUrl = pictureUrl) }
    }

    private fun onEditConfirmed() {
        val pictureUrl = _uiState.value.selectedPictureUrl ?: return
        if (pictureUrl == _uiState.value.currentPictureUrl) { // same picture as before -- skip update
            onPictureSaved()
            return
        }

        val userId = _uiState.value.userId ?: return

        coroutineScope.launch {
            updateUserPictureUseCase(userId = userId, pictureUri = pictureUrl)
                .onSuccess {
                    onPictureSaved()
                }
                .onFailure {
                }
        }
    }

    private fun getUserData(): Flow<Result<User>> {
        return flow {
            val userId = firebaseUser?.uid ?: return@flow
            emit(getUserByIdUseCase(userId))
        }
    }
}