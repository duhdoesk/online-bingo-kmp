package ui.presentation.util

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