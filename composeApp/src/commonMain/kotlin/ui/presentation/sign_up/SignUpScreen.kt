package ui.presentation.sign_up

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.presentation.sign_up.event.SignUpScreenEvent
import ui.presentation.sign_up.screens.SignUpScreenOrientation
import ui.presentation.util.WindowInfo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SignUpScreen(
    component: SignUpScreenComponent,
    windowInfo: WindowInfo
) {

//    val uiState = component
//        .uiState
//        .collectAsState()
//        .value
//
//    val isFormValid = component
//        .isFormValid
//        .collectAsState()
//        .value
//
//    val signUpErrorDialogState = component
//        .signUpErrorDialogState
//
//    SignUpScreenOrientation(windowInfo = windowInfo,
//        uiState = uiState,
//        isFormValid = isFormValid,
//        signUpErrorDialogState = signUpErrorDialogState,
//        event = { event ->
//            when (event) {
//                is SignUpScreenEvent.PopBack ->
//                    component.popBack()
//
//                is SignUpScreenEvent.SignUp ->
//                    component.signUp()
//
//                is SignUpScreenEvent.UpdateEmail ->
//                    component.updateEmail(event.email)
//
//                is SignUpScreenEvent.UpdatePassword1 ->
//                    component.updatePassword1(event.password)
//
//                is SignUpScreenEvent.UpdatePassword2 ->
//                    component.updatePassword2(event.password)
//            }
//        }
//    )
}