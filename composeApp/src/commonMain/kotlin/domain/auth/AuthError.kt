package domain.auth

import getPlatform
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.StringResource
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.auth_email_already_exists
import themedbingo.composeapp.generated.resources.auth_invalid_credential
import themedbingo.composeapp.generated.resources.auth_too_many_requests
import themedbingo.composeapp.generated.resources.auth_unmapped_error
import themedbingo.composeapp.generated.resources.auth_user_not_found

@OptIn(ExperimentalResourceApi::class)
fun getAuthErrorDescription(errorMessage: String): StringResource {

    val platform = getPlatform()

    if (platform.name.contains("Android")) {
        return when (errorMessage) {
            "There is no user record corresponding to this identifier. The user may have been deleted." ->
                Res.string.auth_user_not_found

            "The password is invalid or the user does not have a password." ->
                Res.string.auth_invalid_credential

            "auth/email-already-exists" ->
                Res.string.auth_email_already_exists

            "auth/too-many-requests" ->
                Res.string.auth_too_many_requests

            else ->
                Res.string.auth_unmapped_error
        }
    } else {
        return if (errorMessage.contains("17011"))
            Res.string.auth_user_not_found

        else if (errorMessage.contains("17009"))
            Res.string.auth_invalid_credential

        else if (errorMessage.contains("17007"))
            Res.string.auth_email_already_exists

        else if (errorMessage.contains("17010"))
            Res.string.auth_too_many_requests

        else
            Res.string.auth_unmapped_error
    }
}