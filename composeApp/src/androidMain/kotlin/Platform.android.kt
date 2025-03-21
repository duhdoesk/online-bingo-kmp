import domain.billing.ANDROID_REVCAT_API_KEY

class AndroidPlatform : Platform {
    override val revCatApiKey: String = ANDROID_REVCAT_API_KEY
    override val system: OperationalSystem = OperationalSystem.ANDROID
}

actual fun getPlatform(): Platform = AndroidPlatform()
