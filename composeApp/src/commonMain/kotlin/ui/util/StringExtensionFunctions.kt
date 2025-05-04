package ui.util

fun String.isEmailValid(): Boolean {
    val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
    return emailRegex.toRegex().matches(this)
}

fun String.isPasswordValid(): Boolean {
    /**
     * Password regex rules:
     * - between 8 and 16 characters
     * - at least one uppercase English letter
     * - at least one lowercase English letter
     * - at least one digit
     */
    val passwordRegex = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{8,16}$"

    return passwordRegex.toRegex().matches(this)
}

fun Int.ordinal(): String {
    return if (this % 100 in 11..13) {
        "${this}th"
    } else {
        when (this % 10) {
            1 -> "${this}st"
            2 -> "${this}nd"
            3 -> "${this}rd"
            else -> "${this}th"
        }
    }
}
