package ui.presentation.room.screens.component

fun getFormattedNumber(number: Int): String {
    return when (number) {
        in 1..9 -> "0$number"
        else -> number.toString()
    }
}