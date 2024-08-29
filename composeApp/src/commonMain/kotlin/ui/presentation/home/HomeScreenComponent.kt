package ui.presentation.home

import com.arkivanov.decompose.ComponentContext
import dev.gitlive.firebase.auth.FirebaseUser
import domain.user.use_case.FlowUserUseCase
import domain.user.use_case.GetUserByIdUseCase
import io.github.jan.supabase.gotrue.user.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import ui.navigation.Configuration
import ui.presentation.home.event.HomeScreenEvent
import ui.presentation.home.state.HomeScreenUIState
import util.componentCoroutineScope

class HomeScreenComponent(
    componentContext: ComponentContext,
    private val supabaseUser: UserInfo,
    private val onNavigate: (configuration: Configuration) -> Unit
) : ComponentContext by componentContext, KoinComponent {

    private val coroutineScope = componentCoroutineScope()

    private val getUserByIdUseCase by inject<FlowUserUseCase>()

    private val _uiState = MutableStateFlow(HomeScreenUIState.INITIAL)
    val uiState = _uiState.asStateFlow()

    fun uiEvent(event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.Navigate -> navigate(event.configuration)
            is HomeScreenEvent.UILoaded -> uiLoaded()
        }
    }

    private fun uiLoaded() {
        coroutineScope.launch {
            getUserByIdUseCase(supabaseUser.id).collect { user ->
                _uiState.update {
                    HomeScreenUIState(
                        loading = false,
                        userName = user.name,
                        userPicture = user.pictureUri
                    )
                }
            }
        }
    }

    private fun navigate(configuration: Configuration) {
        onNavigate(configuration)
    }
}