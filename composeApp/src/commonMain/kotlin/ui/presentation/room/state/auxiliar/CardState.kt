package ui.presentation.room.state.auxiliar

sealed class CardState {
    data object Error : CardState()
    data object Loading : CardState()
    data class Success(val items: List<String>) : CardState()
}
