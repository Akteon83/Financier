package com.atech.financier.ui.navigation

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/** Интерфейс, описывающий внутренний экран приложения */
@Serializable
sealed interface Screen {

    @Serializable
    @SerialName("Expenses")
    object Expenses : Screen

    @Serializable
    @SerialName("Revenues")
    object Revenues : Screen

    @Serializable
    @SerialName("Account")
    object Account : Screen

    @Serializable
    @SerialName("Categories")
    object Categories : Screen

    @Serializable
    @SerialName("Settings")
    object Settings : Screen

    @Serializable
    @SerialName("History")
    data class History(
        val isIncome: Boolean,
    ) : Screen

    @Serializable
    @SerialName("AccountEditor")
    object AccountEditor : Screen

    @Serializable
    @SerialName("TransactionEditor")
    data class TransactionEditor(
        val isIncome: Boolean,
        val id: Int,
    ) : Screen

    @Serializable
    @SerialName("Analysis")
    data class Analysis(
        val isIncome: Boolean,
    ) : Screen
}
