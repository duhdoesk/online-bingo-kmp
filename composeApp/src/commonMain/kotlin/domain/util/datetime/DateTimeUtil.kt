package domain.util.datetime

import androidx.compose.ui.text.intl.Locale
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.DateTimeFormat
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char

fun getCurrentDateTime(): Long =
    Clock.System.now().toEpochMilliseconds()

fun formatDateTime(): DateTimeFormat<LocalDateTime> {
    val locale = Locale.current.language

    return when (locale) {
        "pt" -> {
            LocalDateTime.Format {
                dayOfMonth(padding = Padding.ZERO)
                chars(" de ")
                monthName(
                    names = MonthNames(
                        "janeiro",
                        "fevereiro",
                        "março",
                        "abril",
                        "maio",
                        "junho",
                        "julho",
                        "agosto",
                        "setembro",
                        "outubro",
                        "novembro",
                        "dezembro"
                    )
                )
                chars(" de ")
                year()
                chars(" às ")
                hour(padding = Padding.ZERO)
                char(':')
                minute(padding = Padding.ZERO)
                char('.')
            }
        }

        else -> {
            LocalDateTime.Format {
                monthName(
                    names = MonthNames(
                        "January",
                        "February",
                        "March",
                        "April",
                        "May",
                        "June",
                        "July",
                        "August",
                        "September",
                        "October",
                        "November",
                        "December"
                    )
                )
                char(' ')
                dayOfMonth(padding = Padding.NONE)
                chars(", ")
                year()
                chars(" at ")
                amPmHour(padding = Padding.ZERO)
                char(':')
                minute(padding = Padding.ZERO)
                char(' ')
                amPmMarker(am = "am", pm = "pm")
                char('.')
            }
        }
    }
}
