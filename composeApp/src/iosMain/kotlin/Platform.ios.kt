import domain.billing.ANDROID_REVCAT_API_KEY
import domain.billing.IOS_REVCAT_API_KEY
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
    override val revCatApiKey: String = IOS_REVCAT_API_KEY
}

actual fun getPlatform(): Platform = IOSPlatform()