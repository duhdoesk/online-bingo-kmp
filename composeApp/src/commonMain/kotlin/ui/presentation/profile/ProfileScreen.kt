package ui.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.unit.dp
import dev.gitlive.firebase.firestore.toMilliseconds
import domain.util.datetime.formatDateTime
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import ui.presentation.util.WindowInfo

@Composable
fun ProfileScreen(component: ProfileScreenComponent, windowInfo: WindowInfo) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(component.firebaseUser.uid)
        Text(component.firebaseUser.displayName ?: "")

        Spacer(Modifier.height(16.dp))

        val user = component
            .user
            .collectAsState()
            .value

        user?.run {
            Text(name)

            val instant = Instant.fromEpochMilliseconds(nameLastUpdated.toMilliseconds().toLong())
            val dateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

            Text(dateTime.format(formatDateTime()))
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { component.signOut() }) {
            Text("Sign Out")
        }

        Spacer(Modifier.height(16.dp))

        Button(onClick = { component.popBack() }) {
            Text("Back")
        }
    }
}