package com.atech.financier.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.atech.financier.ui.util.MockData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CategoriesViewModel : ViewModel() {

    private val _state = MutableStateFlow(CategoriesState(categories = MockData.categoriesList))
    val state: StateFlow<CategoriesState> = _state.asStateFlow()

    fun onSearchChange(search: String) {
        _state.update { currentState ->
            currentState.copy(
                search = search
            )
        }
    }

}

@Immutable
data class CategoriesState(
    val search: String = "",
    val categories: List<CategoryItemState> = emptyList(),
)

@Immutable
data class CategoryItemState(
    val title: String = "",
    val emoji: String = "",
)
