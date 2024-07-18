package ui.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.presentation.profile.event.ProfileScreenEvent
import ui.presentation.profile.screens.ProfileScreenOrientation
import ui.presentation.util.WindowInfo

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ProfileScreen(component: ProfileScreenComponent, windowInfo: WindowInfo) {

    val user = component
        .user
        .collectAsState()
        .value

    val successDialog = component.successDialog
    val errorDialog = component.errorDialog
    val updateNameDialog = component.updateNameDialog
    val updateVictoryMessageDialog = component.updateVictoryMessageDialog
    val updatePasswordDialog = component.updatePasswordDialog

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
                        component.signOut()

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
        //todo(): show dialog
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

}