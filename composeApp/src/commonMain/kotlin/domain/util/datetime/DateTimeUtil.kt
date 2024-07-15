package domain.util.datetime

import kotlinx.datetime.Clock

fun getCurrentDateTime(): Long =
    Clock.System.now().toEpochMilliseconds()