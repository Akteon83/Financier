package com.atech.financier.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class CategoriesViewModel : ViewModel() {

    var state by mutableStateOf(
        CategoriesState(
            categories = listOf(

                CategoryItemState(
                    title = "Аренда Квартиры",
                    emoji = "\uD83C\uDFE0",
                ),

                CategoryItemState(
                    title = "Одежда",
                    emoji = "\uD83D\uDC57",
                ),

                CategoryItemState(
                    title = "На собачку",
                    emoji = "\uD83D\uDC36",
                ),

                CategoryItemState(
                    title = "Ремонт квартиры",
                    emoji = "РК",
                ),

                CategoryItemState(
                    title = "Продукты",
                    emoji = "\uD83C\uDF6D",
                ),

                CategoryItemState(
                    title = "Спортзал",
                    emoji = "\uD83C\uDFCB\uFE0F",
                ),

                CategoryItemState(
                    title = "Медицина",
                    emoji = "\uD83D\uDC8A",
                ),
            ),
        )
    )
        private set

    fun onSearchChange(search: String) {
        state = state.copy(
            search = search
        )
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
