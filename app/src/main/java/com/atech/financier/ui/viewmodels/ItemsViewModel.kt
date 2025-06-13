package com.atech.financier.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import com.atech.financier.domain.models.Category

class ItemsViewModel {

    val items = mutableStateListOf<Category>(
        Category(
            id = 0,
            name = "Аренда Квартиры",
            emoji = "\uD83C\uDFE0",
            isIncome = false,
        ),
        Category(
            id = 1,
            name = "Одежда",
            emoji = "\uD83D\uDC57",
            isIncome = false,
        ),
        Category(
            id = 2,
            name = "На собачку",
            emoji = "\uD83D\uDC36",
            isIncome = false,
        ),
        Category(
            id = 3,
            name = "Ремонт квартиры",
            emoji = "РК",
            isIncome = false,
        ),
        Category(
            id = 4,
            name = "Продукты",
            emoji = "\uD83C\uDF6D",
            isIncome = false,
        ),
        Category(
            id = 5,
            name = "Спортзал",
            emoji = "\uD83C\uDFCB\uFE0F",
            isIncome = false,
        ),
        Category(
            id = 6,
            name = "Медицина",
            emoji = "\uD83D\uDC8A",
            isIncome = false,
        ),
    )
}