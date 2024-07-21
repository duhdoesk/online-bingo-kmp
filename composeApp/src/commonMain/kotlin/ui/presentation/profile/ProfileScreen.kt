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
import themedbingo.composeapp.generated.resources.delete_account_body
import themedbingo.composeapp.generated.resources.delete_account_title
import themedbingo.composeapp.generated.resources.sign_out_dialog_body
import themedbingo.composeapp.generated.resources.sign_out_dialog_title
import themedbingo.composeapp.generated.resources.success
import ui.presentation.profile.event.ProfileScreenEvent
import ui.presentation.profile.screens.ProfileScreenOrientation
import ui.presentation.util.dialog.GenericActionDialog
import ui.presentation.util.WindowInfo
import ui.presentation.util.dialog.GenericErrorDialog
import ui.presentation.util.dialog.GenericSuccessDialog
import ui.presentation.util.dialog.UpdateNameDialog
import ui.presentation.util.dialog.UpdateVictoryMessageDialog

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
    val signOutDialog = component.signOutDialogState
    val deleteAccountDialog = component.deleteAccountDialogState

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
        GenericErrorDialog(
            onDismiss = { errorDialog.hideDialog() },
            body = errorDialog.dialogData.value,
        )
    }

    if (updateNameDialog.isVisible.value) {
        UpdateNameDialog(
            onDismiss = { updateNameDialog.hideDialog() },
            onConfirm = { component.updateName(it) },
            currentName = user?.name ?: "",
        )
    }

    if (updateVictoryMessageDialog.isVisible.value) {
        UpdateVictoryMessageDialog(
            onDismiss = { updateVictoryMessageDialog.hideDialog() },
            onConfirm = { component.updateVictoryMessage(it) },
            currentVictoryMessage = user?.victoryMessage ?: "",
        )
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

    if (deleteAccountDialog.isVisible.value) {
        GenericActionDialog(
            onDismiss = { deleteAccountDialog.hideDialog() },
            onConfirm = {
                deleteAccountDialog.hideDialog()
                component.deleteAccount()
            },
            title = Res.string.delete_account_title,
            body = Res.string.delete_account_body,
        )
    }

}