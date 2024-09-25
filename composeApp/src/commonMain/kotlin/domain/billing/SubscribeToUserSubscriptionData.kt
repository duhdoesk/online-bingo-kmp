package domain.billing

import com.revenuecat.purchases.kmp.CustomerInfo
import com.revenuecat.purchases.kmp.Purchases
import com.revenuecat.purchases.kmp.PurchasesDelegate
import com.revenuecat.purchases.kmp.PurchasesError
import com.revenuecat.purchases.kmp.models.StoreProduct
import com.revenuecat.purchases.kmp.models.StoreTransaction
import domain.billing.model.UserSubscriptionData
import kotlinx.coroutines.flow.MutableStateFlow

class SubscribeToUserSubscriptionData {

    private val subscriptionState = MutableStateFlow(UserSubscriptionData(false))

    init {
        Purchases.sharedInstance.delegate = object : PurchasesDelegate {
            override fun onCustomerInfoUpdated(customerInfo: CustomerInfo) {
                subscriptionState.value = UserSubscriptionData(
                    isSubscribed = customerInfo.entitlements.all.isNotEmpty()
                )
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

    operator fun invoke() = subscriptionState
}