package com.atech.financier.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screen {

    @Serializable
    object Expenses : Screen

    @Serializable
    object Revenues : Screen

    @Serializable
    object Account : Screen

    @Serializable
    object Categories : Screen

    @Serializable
    object Settings : Screen

    @Serializable
    object ExpensesHistory : Screen

    @Serializable
    object RevenuesHistory : Screen
}
