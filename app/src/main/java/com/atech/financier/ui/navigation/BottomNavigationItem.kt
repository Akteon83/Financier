package com.atech.financier.ui.navigation

import com.atech.financier.R

/** Класс, описывающий элемент нижней панели навигации */
data class BottomNavigationItem<T : Screen>(
    val label: Int,
    val icon: Int,
    val route: T
) {
    companion object {

        val navigationBarItems = listOf(

            BottomNavigationItem(
                R.string.expenses,
                R.drawable.expenses,
                Screen.Expenses
            ),

            BottomNavigationItem(
                R.string.revenues,
                R.drawable.revenues,
                Screen.Revenues
            ),

            BottomNavigationItem(
                R.string.account,
                R.drawable.account,
                Screen.Account
            ),

            BottomNavigationItem(
                R.string.categories,
                R.drawable.categories,
                Screen.Categories
            ),

            BottomNavigationItem(
                R.string.settings,
                R.drawable.settings,
                Screen.Settings
            )
        )
    }
}
