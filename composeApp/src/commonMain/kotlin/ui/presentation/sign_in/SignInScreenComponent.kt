package ui.presentation.sign_in

import com.arkivanov.decompose.ComponentContext

class SignInScreenComponent(
    componentContext: ComponentContext,
    private val onSignIn: () -> Unit
): ComponentContext by componentContext {

    fun signIn() =
        onSignIn()
}