package com.atech.financier.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.financier.data.repository.CategoryRepositoryImpl
import com.atech.financier.ui.mapper.toCategoryItemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CategoriesViewModel : ViewModel() {

    private val _state = MutableStateFlow(CategoriesState())
    val state: StateFlow<CategoriesState> = _state.asStateFlow()

    fun onSearchChange(search: String) {
        _state.update { currentState ->
            currentState.copy(
                search = search
            )
        }
    }

    fun loadCategories(requireUpdate: Boolean = false) {

        viewModelScope.launch {

            val categories = CategoryRepositoryImpl
                .getCategories(
                    requireUpdate = requireUpdate,
                )

            _state.update { currentState ->
                currentState.copy(
                    categories = categories.map { it.toCategoryItemState() }
                )
            }
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
    val id: Int,
    val title: String = "",
    val emoji: String = "",
)
