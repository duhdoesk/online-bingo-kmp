package ui.presentation.paywall

import com.arkivanov.decompose.ComponentContext
import org.koin.core.component.KoinComponent

class PaywallScreenViewModel(
    componentContext: ComponentContext,
    val onDismiss: () -> Unit
) : ComponentContext by componentContext, KoinComponent {
    fun dismissPaywall() = onDismiss()
}
