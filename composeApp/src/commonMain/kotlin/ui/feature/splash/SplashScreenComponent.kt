package ui.feature.splash

import com.arkivanov.decompose.ComponentContext
import domain.feature.appVersion.model.AppVersionUpdate
import domain.feature.appVersion.useCase.CheckForUpdatesUseCase
import domain.feature.auth.useCase.GetSessionStatusUseCase
import domain.util.resource.Resource
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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
    private val onSignedIn: () -> Unit,
    private val onUpdateRequired: (updateUrl: String) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()
    private val getSessionStatusUseCase: GetSessionStatusUseCase by inject()
    private val checkForUpdatesUseCase: CheckForUpdatesUseCase by inject()

    private val _progress = MutableStateFlow<Float>(0f)
    val progress = _progress.asStateFlow()

    init {
        coroutineScope.launch {
            delay(1000)

            _progress.update { 0.5f }
            delay(1000)
            checkForUpdates()

            _progress.update { 1f }
            delay(1000)
            checkAuthentication()
        }
    }

    private suspend fun checkForUpdates() {
        val resource = checkForUpdatesUseCase.invoke().first()
        if (resource is Resource.Success && resource.data is AppVersionUpdate.UpdateRequired) {
            onUpdateRequired(resource.data.updateUrl)
        }
    }

    private suspend fun checkAuthentication() {
        val resource = getSessionStatusUseCase().first()
        when (resource) {
            is Resource.Success -> {
                if (resource.data is SessionStatus.Authenticated) {
                    onSignedIn()
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
