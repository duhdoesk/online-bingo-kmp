package ui.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import themedbingo.composeapp.generated.resources.Res
import themedbingo.composeapp.generated.resources.sign_out_dialog_body
import themedbingo.composeapp.generated.resources.sign_out_dialog_title
import themedbingo.composeapp.generated.resources.success
import ui.presentation.profile.event.ProfileScreenEvent
import ui.presentation.profile.screens.ProfileScreenOrientation
import ui.presentation.util.GenericActionDialog
import ui.presentation.util.WindowInfo
import ui.presentation.util.dialog.GenericSuccessDialog

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileScreen(component: ProfileScreenComponent, windowInfo: WindowInfo) {

    val user = component
        .user
        .collectAsState()
        .value

    val successDialog = component.successDialogState
    val errorDialog = component.errorDialogState
    val updateNameDialog = component.updateNameDialogState
    val updateVictoryMessageDialog = component.updateVictoryMessageDialogState
    val updatePasswordDialog = component.updatePasswordDialogState
    val signOutDialog = component.signOutDialogState

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {

        ProfileScreenOrientation(
            windowInfo = windowInfo,
            user = user,
            event = { profileScreenEvent ->
                when (profileScreenEvent) {
                    ProfileScreenEvent.DeleteAccount ->
                        component.deleteAccount()

                    ProfileScreenEvent.PopBack ->
                        component.popBack()

                    ProfileScreenEvent.SignOut ->
                        component.signOutDialogState.showDialog(null)

                    is ProfileScreenEvent.UpdateName ->
                        component.updateName(profileScreenEvent.name)

                    is ProfileScreenEvent.UpdatePassword ->
                        component.updatePassword(
                            newPassword = profileScreenEvent.newPassword,
                            currentPassword = profileScreenEvent.currentPassword
                        )

                    ProfileScreenEvent.UpdatePicture ->
                        component.updatePicture()

                    is ProfileScreenEvent.UpdateVictoryMessage ->
                        component.updateVictoryMessage(profileScreenEvent.victoryMessage)
                }
            }
        )
//        Text(component.firebaseUser.uid)
//        Text(component.firebaseUser.displayName ?: "")
//
//        Spacer(Modifier.height(16.dp))
//
//        val user = component
//            .user
//            .collectAsState()
//            .value
//
//        user?.run {
//            Text(name)
//
//            val instant = Instant.fromEpochMilliseconds(nameLastUpdated.toMilliseconds().toLong())
//            val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
//
//            Text(dateTime.format(formatDateTime()))
//        }

//        Spacer(Modifier.height(16.dp))
//
//        Button(onClick = { component.signOut() }) {
//            Text("Sign Out")
//        }
//
//        Spacer(Modifier.height(16.dp))
//
//        Button(onClick = { component.popBack() }) {
//            Text("Back")
//        }
    }

    if (successDialog.isVisible.value) {
        GenericSuccessDialog(
            onDismiss = { successDialog.hideDialog() },
            stringRes = successDialog.dialogData.value ?: Res.string.success
        )
    }

    if (errorDialog.isVisible.value) {
        //todo(): show dialog
    }

    if (updateNameDialog.isVisible.value) {
        //todo(): show dialog
    }

    if (updateVictoryMessageDialog.isVisible.value) {
        //todo(): show dialog
    }

    if (updatePasswordDialog.isVisible.value) {
        //todo(): show dialog
    }

    if (signOutDialog.isVisible.value) {
        GenericActionDialog(
            onDismiss = { signOutDialog.hideDialog() },
            onConfirm = {
                signOutDialog.hideDialog()
                component.signOut()
            },
            title = Res.string.sign_out_dialog_title,
            body = Res.string.sign_out_dialog_body,
        )
    }
}