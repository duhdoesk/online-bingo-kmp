package ui.feature.home

import com.arkivanov.decompose.ComponentContext
import domain.feature.user.useCase.GetCurrentUserUseCase
import domain.util.resource.Resource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.navigation.Configuration
import util.componentCoroutineScope

@OptIn(ExperimentalResourceApi::class)
class HomeScreenComponent(
    componentContext: ComponentContext,
    private val onNavigate: (configuration: Configuration) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()
    private val getCurrentUserUseCase: GetCurrentUserUseCase by inject()

    val uiState: StateFlow<HomeUiState> = getCurrentUserUseCase.invoke()
        .map { resource ->
            when (resource) {
                is Resource.Success -> {
                    HomeUiState(
                        isLoading = false,
                        username = resource.data.name,
                        message = resource.data.victoryMessage,
                        pictureUrl = resource.data.pictureUri,
                        tier = resource.data.tier
                    )
                }

                is Resource.Failure -> {
                    HomeUiState()
                }
            }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.Companion.WhileSubscribed(5_000),
            initialValue = HomeUiState()
        )

    fun navigate(configuration: Configuration) {
        onNavigate(configuration)
    }
}
