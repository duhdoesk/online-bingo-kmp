package ui.presentation.util.bottom_sheet

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import com.revenuecat.purchases.kmp.ui.revenuecatui.Paywall
import com.revenuecat.purchases.kmp.ui.revenuecatui.PaywallOptions

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaywallBottomSheet(
    paywallOptions: PaywallOptions,
    sheetState: SheetState,
    onDismiss: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
    ) {
        Paywall(paywallOptions)
    }
}