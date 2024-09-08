import android.os.Build
import domain.billing.ANDROID_REVCAT_API_KEY

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
    override val revCatApiKey: String = ANDROID_REVCAT_API_KEY
}

actual fun getPlatform(): Platform = AndroidPlatform()