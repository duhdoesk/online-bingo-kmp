package ui.feature.createRoom

import domain.room.model.BingoType
import domain.room.model.RoomPrivacy
import domain.theme.model.Theme

data class CreateRoomUiState(
    val isLoading: Boolean = true,
    val canProceed: Boolean = false,
    val name: String = "",
    val privacy: RoomPrivacy = RoomPrivacy.Open,
    val maxWinners: Int = 1,
    val type: Type = Type.Classic
) {
    sealed class Type {
        data class Themed(val theme: Theme?) : Type()
        data object Classic : Type()

        fun toEnum(): BingoType {
            return when (this) {
                Classic -> BingoType.CLASSIC
                is Themed -> BingoType.THEMED
            }
        }

        companion object {
            fun parseFromEnum(enum: BingoType): Type {
                return when (enum) {
                    BingoType.CLASSIC -> Classic
                    BingoType.THEMED -> Themed(null)
                }
            }
        }
    }
}
