package ui.feature.createRoom.screens.components

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.theme.model.Theme
import domain.theme.useCase.GetAvailableThemes
import domain.util.resource.Resource
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class EditThemeCardViewModel(getAvailableThemes: GetAvailableThemes) : ViewModel() {

    private val _themes = getAvailableThemes()

    val uiState = _themes.map { themes ->
        when (themes) {
            is Resource.Failure -> {
                EditThemeCardUiState(
                    isLoading = false,
                    isError = true
                )
            }

            is Resource.Success -> {
                EditThemeCardUiState(
                    isLoading = false,
                    isError = false,
                    availableThemes = themes.data
                )
            }
        }
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = EditThemeCardUiState(isLoading = true)
        )
}

data class EditThemeCardUiState(
    val isLoading: Boolean = true,
    val isError: Boolean = false,
    val availableThemes: List<Theme> = emptyList()
)
