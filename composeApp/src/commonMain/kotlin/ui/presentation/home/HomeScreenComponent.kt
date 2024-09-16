package ui.presentation.home

import androidx.compose.runtime.mutableStateOf
import com.arkivanov.decompose.ComponentContext
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.result.awaitCustomerInfoResult
import domain.user.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ui.navigation.Configuration
import ui.presentation.home.event.HomeScreenEvent
import ui.presentation.home.state.HomeScreenUIState
import util.componentCoroutineScope

class HomeScreenComponent(
    componentContext: ComponentContext,
    private val user: Flow<User?>,
    private val onNavigate: (configuration: Configuration) -> Unit
) : ComponentContext by componentContext {

    private val coroutineScope = componentCoroutineScope()

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

            val customerInfo = Purchases.sharedInstance.awaitCustomerInfoResult().getOrNull()
            val isSubscribed = when (customerInfo == null) {
                true -> false
                false -> (customerInfo.entitlements["Premium Subscription"]?.isActive == true)
            }

            user.collect { user ->
                if (user != null) {
                    _uiState.update {
                        HomeScreenUIState(
                            loading = false,
                            userName = user.name,
                            userPicture = user.pictureUri,
                            isSubscribed = isSubscribed,
                        )
                    }
                }
            }
        }
    }

    private fun navigate(configuration: Configuration) {
        onNavigate(configuration)
    }
}