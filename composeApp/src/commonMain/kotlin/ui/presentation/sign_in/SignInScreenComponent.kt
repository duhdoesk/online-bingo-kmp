package ui.presentation.sign_in

import com.arkivanov.decompose.ComponentContext
import domain.user.model.User

class SignInScreenComponent(
    componentContext: ComponentContext,
    private val onSignIn: (user: User) -> Unit
): ComponentContext by componentContext {

    fun signIn() =
        onSignIn(User(id = "007", name = "Eduardo", pictureUri = ""))
}