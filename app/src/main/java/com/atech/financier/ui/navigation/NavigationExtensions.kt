package com.atech.financier.ui.navigation

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute

/** Extension-свойство для получения информации о текущем экране из NavDestination */
val NavDestination.screen
    get() = when {
        this.hasRoute(Screen.Expenses::class) == true -> Screen.Expenses
        this.hasRoute(Screen.Revenues::class) == true -> Screen.Revenues
        this.hasRoute(Screen.Account::class) == true -> Screen.Account
        this.hasRoute(Screen.Categories::class) == true -> Screen.Categories
        this.hasRoute(Screen.Settings::class) == true -> Screen.Settings
        this.hasRoute(Screen.ExpensesHistory::class) == true -> Screen.ExpensesHistory
        this.hasRoute(Screen.RevenuesHistory::class) == true -> Screen.RevenuesHistory
        this.hasRoute(Screen.AccountEditor::class) == true -> Screen.AccountEditor
        else -> Screen.Expenses
    }
