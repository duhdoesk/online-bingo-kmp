package domain.auth

import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.auth_email_already_exists
import themedbingo.composeapp.generated.resources.auth_empty_string
import themedbingo.composeapp.generated.resources.auth_invalid_credential
import themedbingo.composeapp.generated.resources.auth_too_many_requests
import themedbingo.composeapp.generated.resources.auth_unmapped_error
import themedbingo.composeapp.generated.resources.auth_user_not_found

@OptIn(ExperimentalResourceApi::class)
fun getAuthErrorDescription(errorMessage: String): StringResource {

    errorMessage.run {
        return if (contains("There is no user record corresponding to this identifier"))
            Res.string.auth_user_not_found

        else if (contains("The password is invalid or the user does not have a password"))
            Res.string.auth_invalid_credential

        else if (contains("The email address is already in use by another account"))
            Res.string.auth_email_already_exists

        else if (contains("We have blocked all requests from this device due to unusual activity"))
            Res.string.auth_too_many_requests

        else if (contains("Given String is empty or null"))
            Res.string.auth_empty_string

        else
            Res.string.auth_unmapped_error
    }
}