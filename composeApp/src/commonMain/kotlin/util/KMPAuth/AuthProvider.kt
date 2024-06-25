package util.KMPAuth

import androidx.compose.runtime.mutableStateOf
import com.mmk.kmpauth.google.GoogleAuthCredentials
import com.mmk.kmpauth.google.GoogleAuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

val webClientID = "746232526782-mnq2lnm9fp2sunpopk0cfp7pu6v9trcu.apps.googleusercontent.com"

class AuthProvider {

    private val _authReady = MutableStateFlow(false)
    val authReady = _authReady.asStateFlow()

    private val provider = GoogleAuthProvider

    fun create() {
        provider
            .create(credentials = GoogleAuthCredentials(serverId = webClientID))
        _authReady.update { true }
    }

    suspend fun signOut() =
        provider
            .create(credentials = GoogleAuthCredentials(serverId = webClientID))
            .signOut()
}