package domain.profilePictures

data class ProfilePictures(
    val categories: List<Category>
) {
    data class Category(
        val name: String,
        val pictures: List<String>
    )
}
