package ui.feature.room.event

sealed class RoomHostEvent {
    data object UiLoaded : RoomHostEvent()
    data object PopBack : RoomHostEvent()
    data object StartRaffle : RoomHostEvent()
    data object FinishRaffle : RoomHostEvent()
    data object RaffleNextItem : RoomHostEvent()
    data object CleanErrors : RoomHostEvent()
}
