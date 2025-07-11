package com.atech.financier.ui.navigation

import com.atech.financier.R

/** Класс, описывающий элемент нижней панели навигации */
enum class BottomNavigationItem(
    val label: Int,
    val icon: Int,
    val route: Screen
) {
    EXPENSES(
        R.string.expenses,
        R.drawable.expenses,
        Screen.Expenses,
    ),

    REVENUES(
        R.string.revenues,
        R.drawable.revenues,
        Screen.Revenues,
    ),

    ACCOUNT(
        R.string.account,
        R.drawable.account,
        Screen.Account,
    ),

    CATEGORIES(
        R.string.categories,
        R.drawable.categories,
        Screen.Categories,
    ),

    SETTINGS(
        R.string.settings,
        R.drawable.settings,
        Screen.Settings,
    ),
}
