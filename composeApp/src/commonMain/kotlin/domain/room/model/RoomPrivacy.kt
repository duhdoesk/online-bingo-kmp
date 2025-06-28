package domain.room.model

sealed class RoomPrivacy {
    data class Private(val password: String) : RoomPrivacy()
    data object Open : RoomPrivacy()

    fun switch(): RoomPrivacy {
        return when (this) {
            is Private -> Open
            is Open -> Private("")
        }
    }
}
