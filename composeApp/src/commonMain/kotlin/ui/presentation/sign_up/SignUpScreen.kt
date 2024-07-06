package ui.presentation.sign_up

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ui.presentation.util.WindowInfo

@Composable
fun SignUpScreen(
    component: SignUpScreenComponent,
    windowInfo: WindowInfo
) {
    Button(onClick = { component.popBack() }) {
        Text("Back")
    }
}