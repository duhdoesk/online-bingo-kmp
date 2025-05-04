package ui.feature.home

import com.arkivanov.decompose.ComponentContext
import domain.billing.hasActiveEntitlements
import domain.feature.user.useCase.GetCurrentUserUseCase
import domain.util.resource.Resource
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
    private val onNavigate: (configuration: Configuration) -> Unit,
    private val onUserNotCreated: () -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()
    private val getSignedInUser: GetCurrentUserUseCase by inject()

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<HomeScreenUIState> = getSignedInUser()
        .map { user ->
            when (user) {
                is Resource.Success -> {
                    val isSubscribed = hasActiveEntitlements()

                    HomeScreenUIState(
                        loading = false,
                        userName = user.data.name,
                        userPicture = user.data.pictureUri,
                        isSubscribed = isSubscribed
                    )
                }

                is Resource.Failure -> {
                    onUserNotCreated()
                    HomeScreenUIState(loading = false)
                }
            }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeScreenUIState()
        )

    fun navigate(configuration: Configuration) {
        onNavigate(configuration)
    }
}
