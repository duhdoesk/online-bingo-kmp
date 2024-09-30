package domain.billing

import com.revenuecat.purchases.kmp.CustomerInfo
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesDelegate
import com.revenuecat.purchases.kmp.PurchasesError
import com.revenuecat.purchases.kmp.ktx.awaitCustomerInfo
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreTransaction
import domain.billing.model.UserSubscriptionData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SubscribeToUserSubscriptionData {

    private val _subscriptionState = MutableStateFlow(UserSubscriptionData(false))
    val subscriptionState = _subscriptionState.asStateFlow()

    fun setupDelegate() {
        Purchases.sharedInstance.delegate = object : PurchasesDelegate {
            override fun onCustomerInfoUpdated(customerInfo: CustomerInfo) {
                _subscriptionState.update {
                    UserSubscriptionData(customerInfo.entitlements.active.isNotEmpty())
                }
            }

            override fun onPurchasePromoProduct(
                product: StoreProduct,
                startPurchase: (
                    onError: (error: PurchasesError, userCancelled: Boolean) -> Unit,
                    onSuccess: (storeTransaction: StoreTransaction, customerInfo: CustomerInfo) -> Unit
                ) -> Unit
            ) = Unit
        }
    }
}