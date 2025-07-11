package com.atech.financier.ui.navigation

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.toRoute
import kotlin.enums.enumEntries

/** Extension-свойства для получения информации о текущих экранах из NavBackStackEntry */

val NavBackStackEntry.screen: Screen
    get() {
        return when {
            this.destination.hasRoute(Screen.Expenses::class) -> this.toRoute<Screen.Expenses>()
            this.destination.hasRoute(Screen.Revenues::class) -> this.toRoute<Screen.Revenues>()
            this.destination.hasRoute(Screen.Account::class) -> this.toRoute<Screen.Account>()
            this.destination.hasRoute(Screen.Categories::class) -> this.toRoute<Screen.Categories>()
            this.destination.hasRoute(Screen.Settings::class) -> this.toRoute<Screen.Settings>()
            this.destination.hasRoute(Screen.History::class) -> this.toRoute<Screen.History>()
            this.destination.hasRoute(Screen.AccountEditor::class) -> this.toRoute<Screen.AccountEditor>()
            this.destination.hasRoute(Screen.TransactionEditor::class) -> this.toRoute<Screen.TransactionEditor>()
            else -> Screen.Expenses
        }
    }

val List<NavBackStackEntry>.selectedBottomItem: BottomNavigationItem
    get() {
        enumEntries<BottomNavigationItem>().reversed().forEach { item ->
            if (this.any { it.destination.hasRoute(item.route::class) }) {
                return item
            }
        }
        return BottomNavigationItem.EXPENSES
    }
