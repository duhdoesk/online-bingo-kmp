@file:OptIn(ExperimentalCoroutinesApi::class)

package ui.feature.profile.component.picture

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import domain.feature.user.useCase.GetProfilePicturesUseCase
import domain.profilePictures.model.ProfilePictures
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class ChangePictureViewModel(getProfilePicturesUseCase: GetProfilePicturesUseCase) : ViewModel() {

    private val selectedCategories = MutableStateFlow<List<ProfilePictures.Category>>(emptyList())

    val uiState: StateFlow<ChangePictureUiState> =
        combine(selectedCategories, getProfilePicturesUseCase()) { selectedCategories, picturesRes ->
            val pictures = picturesRes.getOrNull() ?: ProfilePictures(emptyList())

            val availablePictures =
                if (selectedCategories.isEmpty()) {
                    pictures.categories.flatMap { it.pictures }
                } else {
                    selectedCategories.flatMap { it.pictures }
                }

            ChangePictureUiState(
                isLoading = false,
                selectedCategories = selectedCategories,
                availablePictures = availablePictures,
                allCategories = pictures.categories
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = ChangePictureUiState()
            )

    fun onUiEvent(event: ChangePictureUiEvent) {
        when (event) {
            is ChangePictureUiEvent.SelectCategory -> selectCategory(event.category)
            ChangePictureUiEvent.ClearCategories -> clearCategories()
        }
    }

    private fun clearCategories() {
        selectedCategories.value = emptyList()
    }

    private fun selectCategory(category: ProfilePictures.Category) {
        val categories = selectedCategories.value.toMutableList()
        if (categories.contains(category)) {
            categories.remove(category)
        } else {
            categories.add(0, category)
        }
        selectedCategories.value = categories
    }
}
