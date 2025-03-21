import domain.billing.IOS_REVCAT_API_KEY

class IOSPlatform : Platform {
    override val revCatApiKey: String = IOS_REVCAT_API_KEY
    override val system: OperationalSystem = OperationalSystem.IOS
}

actual fun getPlatform(): Platform = IOSPlatform()
