package ui.feature.paywall

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions

@Composable
fun PaywallScreen(
    viewModel: PaywallScreenViewModel
) {
    /**
     * Paywall State, Options and Visibility
     */
    val paywallOptions = remember {
        PaywallOptions(dismissRequest = { viewModel.dismissPaywall() }) {
            shouldDisplayDismissButton = true
        }
    }

    /**
     * Paywall Call
     */
    Box { Paywall(paywallOptions) }
}
