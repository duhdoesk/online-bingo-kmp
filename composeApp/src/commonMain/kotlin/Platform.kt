interface Platform {
    val name: String
    val revCatApiKey: String
}

expect fun getPlatform(): Platform