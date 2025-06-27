package com.atech.financier.ui.util

import com.atech.financier.ui.viewmodel.CategoryItemState
import com.atech.financier.ui.viewmodel.ExpenseItemState
import com.atech.financier.ui.viewmodel.RevenueItemState
import com.atech.financier.ui.viewmodel.TransactionItemState

/** Синглтон, хранящий моковые данные для тестирования UI */
object MockData {

    val expensesList = listOf(

        ExpenseItemState(
            title = "Аренда Квартиры",
            amount = "100 000",
            emoji = "\uD83C\uDFE0",
        ),

        ExpenseItemState(
            title = "Одежда",
            amount = "100 000",
            emoji = "\uD83D\uDC57",
        ),

        ExpenseItemState(
            title = "На собачку",
            amount = "100 000",
            emoji = "\uD83D\uDC36",
            description = "Джек",
        ),

        ExpenseItemState(
            title = "На собачку",
            amount = "100 000",
            emoji = "\uD83D\uDC36",
            description = "Энни",
        ),

        ExpenseItemState(
            title = "Ремонт Квартиры",
            amount = "100 000",
            emoji = "РК",
        ),

        ExpenseItemState(
            title = "Продукты",
            amount = "100 000",
            emoji = "\uD83C\uDF6D",
        ),

        ExpenseItemState(
            title = "Спортзал",
            amount = "100 000",
            emoji = "\uD83C\uDFCB\uFE0F",
        ),

        ExpenseItemState(
            title = "Медицина",
            amount = "100 000",
            emoji = "\uD83D\uDC8A",
        ),
    )

    val revenuesList = listOf(

        RevenueItemState(
            title = "Зарплата",
            amount = "500 000",
        ),

        RevenueItemState(
            title = "Подработка",
            amount = "100 000",
        ),
    )

    val categoriesList = listOf(

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
    )

    val historyList = listOf(

        TransactionItemState(
            title = "Ремонт Квартиры",
            amount = "100 000",
            description = "Ремонт - фурнитура для дверей",
            emoji = "РК",
            time = "22:01",
        ),

        TransactionItemState(
            title = "Одежда",
            amount = "100 000",
            description = "Футболка с Металликой",
            emoji = "\uD83D\uDC57",
            time = "20:57",
        ),

        TransactionItemState(
            title = "На собачку",
            amount = "100 000",
            description = "Корм для Джека",
            emoji = "\uD83D\uDC36",
            time = "20:11",
        ),

        TransactionItemState(
            title = "На собачку",
            amount = "100 000",
            description = "Корм для Энни",
            emoji = "\uD83D\uDC36",
            time = "20:10",
        ),

        TransactionItemState(
            title = "Спортзал",
            amount = "100 000",
            emoji = "\uD83C\uDFCB\uFE0F",
            time = "11:45",
        ),
    )
}