interface Platform {
    val revCatApiKey: String
    val system: OperationalSystem
}

expect fun getPlatform(): Platform

enum class OperationalSystem {
    ANDROID, IOS
}
