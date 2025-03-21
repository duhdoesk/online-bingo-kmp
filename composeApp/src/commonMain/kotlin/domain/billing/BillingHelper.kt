package domain.billing

import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.result.awaitCustomerInfoResult

suspend fun hasActiveEntitlements(): Boolean {
    Purchases.sharedInstance.invalidateCustomerInfoCache()
    Purchases.sharedInstance.awaitCustomerInfoResult().fold(
        onSuccess = { customerInfo -> return customerInfo.entitlements.active.isNotEmpty() },
        onFailure = { return false }
    )
}
