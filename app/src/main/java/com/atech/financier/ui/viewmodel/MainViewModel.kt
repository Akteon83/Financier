package com.atech.financier.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import com.atech.financier.ui.navigation.Screen

class MainViewModel : ViewModel() /*{

    var state by mutableStateOf(MainState())
        private set

    fun onScreenChange(destination: NavDestination?) {
        state = state.copy(
            currentScreen = when {
                destination?.hasRoute(Screen.Expenses::class) == true -> Screen.Expenses
                destination?.hasRoute(Screen.Revenues::class) == true -> Screen.Revenues
                destination?.hasRoute(Screen.Account::class) == true -> Screen.Account
                destination?.hasRoute(Screen.Categories::class) == true -> Screen.Categories
                destination?.hasRoute(Screen.Settings::class) == true -> Screen.Settings
                destination?.hasRoute(Screen.ExpensesHistory::class) == true -> Screen.ExpensesHistory
                destination?.hasRoute(Screen.RevenuesHistory::class) == true -> Screen.RevenuesHistory
                else -> {
                    Screen.Expenses
                }
            }
        )
    }

}

data class MainState(
    val currentScreen: Screen = Screen.Expenses,
)*/
