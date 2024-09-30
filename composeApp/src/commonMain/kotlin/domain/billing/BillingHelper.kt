package domain.billing

import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.ktx.awaitCustomerInfo

suspend fun hasActiveEntitlements(): Boolean {
    return Purchases.sharedInstance.awaitCustomerInfo().entitlements.active.isNotEmpty()
}