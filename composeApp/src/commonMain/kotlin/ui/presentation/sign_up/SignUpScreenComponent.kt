package ui.presentation.sign_up

import com.arkivanov.decompose.ComponentContext
import domain.auth.getAuthErrorDescription
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.presentation.sign_up.state.SignUpScreenUIState
import ui.presentation.util.dialog.dialog_state.mutableDialogStateOf
import ui.presentation.util.isEmailValid
import ui.presentation.util.isPasswordValid
import util.componentCoroutineScope

class SignUpScreenComponent(
    componentContext: ComponentContext,
    private val onSignUp: () -> Unit,
    private val onPopBack: () -> Unit,
) : ComponentContext by componentContext, KoinComponent {

//    //    private val authService by inject<AuthService>()
//    private val createUserUseCase by inject<CreateUserUseCase>()
//
//    private val _uiState = MutableStateFlow(SignUpScreenUIState())
//    val uiState = _uiState
//        .onEach { state ->
//            _isFormValid.update {
//                state.email.isEmailValid() &&
//                        state.password1.isPasswordValid() &&
//                        state.password2.isPasswordValid() &&
//                        state.password1 == state.password2
//            }
//        }
//        .stateIn(
//            componentContext.componentCoroutineScope(),
//            SharingStarted.WhileSubscribed(),
//            SignUpScreenUIState()
//        )
//
//    private val _isFormValid = MutableStateFlow(false)
//    val isFormValid = _isFormValid
//        .stateIn(
//            componentContext.componentCoroutineScope(),
//            SharingStarted.WhileSubscribed(),
//            false
//        )
//
//    @OptIn(ExperimentalResourceApi::class)
//    val signUpErrorDialogState = mutableDialogStateOf<StringResource?>(null)
//
//    fun updateEmail(email: String) {
//        _uiState.update { state ->
//            state.copy(email = email.trim())
//        }
//    }
//
//    fun updatePassword1(password: String) {
//        _uiState.update { state ->
//            state.copy(password1 = password.trim())
//        }
//    }
//
//    fun updatePassword2(password: String) {
//        _uiState.update { state ->
//            state.copy(password2 = password.trim())
//        }
//    }
//
////    @OptIn(ExperimentalResourceApi::class)
////    fun signUp() {
////        uiState.value.run {
////            componentCoroutineScope().launch {
////                try {
////                    authService
////                        .createUser(email, password1)
////                        .user?.let { onSignUp() }
////
////                } catch (e: Exception) {
////                    println(e.cause)
////                    println(e.message)
////
////                    signUpErrorDialogState.showDialog(getAuthErrorDescription(e.message.orEmpty()))
////                    clearPassword()
////                }
////            }
////        }
////    }
//
//    @OptIn(ExperimentalResourceApi::class)
//    fun signUp() {
//        uiState.value.run {
//            componentCoroutineScope().launch {
//                createUserUseCase.invoke(
//                    email = email,
//                    password = password1
//                )
//                    .onSuccess { onSignUp() }
//                    .onFailure { exception ->
//                        signUpErrorDialogState
//                            .showDialog(getAuthErrorDescription(exception.message.orEmpty()))
//                        clearPassword()
//                    }
//            }
//        }
//    }
//
//    fun popBack() =
//        onPopBack()
//
//    private fun clearPassword() {
//        _uiState.update { state ->
//            state.copy(
//                password1 = "",
//                password2 = "",
//            )
//        }
//    }
}