package ui.presentation.sign_in

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ui.presentation.util.WindowInfo

@Composable
fun SignInScreen(
    component: SignInScreenComponent,
    windowInfo: WindowInfo
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Button(onClick = { component.signIn() }) {
            Text("Sign In")
        }
    }
}