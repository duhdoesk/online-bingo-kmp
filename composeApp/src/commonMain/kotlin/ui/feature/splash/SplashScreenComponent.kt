package ui.feature.splash

import com.arkivanov.decompose.ComponentContext
import domain.feature.auth.useCase.GetSessionStatusUseCase
import domain.util.resource.Resource
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import util.componentCoroutineScope

@OptIn(ExperimentalResourceApi::class)
class SplashScreenComponent(
    componentContext: ComponentContext,
    private val onNotSignedIn: () -> Unit,
    private val onSignedIn: (userId: String?) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()
    private val getSessionStatusUseCase: GetSessionStatusUseCase by inject()

    private val _progress = MutableStateFlow<Float>(0f)
    val progress = _progress.asStateFlow()

    init {
        coroutineScope.launch {
            delay(1000)
            checkAuthentication()
        }
    }

    private suspend fun checkAuthentication() {
        getSessionStatusUseCase().collect { resource ->
            _progress.update { 1f }
            delay(2000)

            when (resource) {
                is Resource.Success -> {
                    if (resource.data is SessionStatus.Authenticated) {
                        onSignedIn(resource.data.session.user?.id)
                    } else {
                        onNotSignedIn()
                    }
                }

                is Resource.Failure -> {
                    onNotSignedIn()
                }
            }
        }
    }
}
