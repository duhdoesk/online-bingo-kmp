package util

import co.touchlab.kermit.Logger

object Log {

    fun d(
        message: String,
        tag: String
    ) {
        Logger.d(
            tag = tag,
            messageString = message
        )
    }

    fun e(
        message: String,
        tag: String,
        throwable: Throwable
    ) {
        Logger.e(
            messageString = message,
            throwable = throwable,
            tag = tag
        )
    }
}
