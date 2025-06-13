package com.atech.financier.ui.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.atech.financier.domain.models.AccountBrief
import com.atech.financier.domain.models.Category
import com.atech.financier.domain.models.TransactionResponse
import java.time.Instant

class ExpensesViewModel : ViewModel() {

    val expenses = mutableStateListOf<TransactionResponse>(
        TransactionResponse(
            id = 0,
            account = AccountBrief(
                id = 0,
                name = "Account",
                balance = "100 000",
                currency = "RUB",
            ),
            category = Category(
                id = 0,
                name = "Аренда Квартиры",
                emoji = "\uD83C\uDFE0",
                isIncome = false,
            ),
            amount = "100 000",
            transactionDate = "2025-06-13T10:22:43.162Z",
            comment = null,
            createdAt = "2025-06-13T10:22:49.297433Z",
            updatedAt = "2025-06-13T10:22:49.297433Z",
        ),
        TransactionResponse(
            id = 1,
            account = AccountBrief(
                id = 0,
                name = "Account",
                balance = "100 000",
                currency = "RUB",
            ),
            category = Category(
                id = 1,
                name = "Одежда",
                emoji = "\uD83D\uDC57",
                isIncome = false,
            ),
            amount = "100 000",
            transactionDate = "2025-06-13T10:22:43.162Z",
            comment = null,
            createdAt = "2025-06-13T10:22:49.297433Z",
            updatedAt = "2025-06-13T10:22:49.297433Z",
        ),
        TransactionResponse(
            id = 2,
            account = AccountBrief(
                id = 0,
                name = "Account",
                balance = "100 000",
                currency = "RUB",
            ),
            category = Category(
                id = 2,
                name = "На собачку",
                emoji = "\uD83D\uDC36",
                isIncome = false,
            ),
            amount = "100 000",
            transactionDate = "2025-06-13T10:22:43.162Z",
            comment = "Джек",
            createdAt = "2025-06-13T10:22:49.297433Z",
            updatedAt = "2025-06-13T10:22:49.297433Z",
        ),
        TransactionResponse(
            id = 3,
            account = AccountBrief(
                id = 0,
                name = "Account",
                balance = "100 000",
                currency = "RUB",
            ),
            category = Category(
                id = 2,
                name = "На собачку",
                emoji = "\uD83D\uDC36",
                isIncome = false,
            ),
            amount = "100 000",
            transactionDate = "2025-06-13T10:22:43.162Z",
            comment = "Энни",
            createdAt = "2025-06-13T10:22:49.297433Z",
            updatedAt = "2025-06-13T10:22:49.297433Z",
        ),
        TransactionResponse(
            id = 4,
            account = AccountBrief(
                id = 0,
                name = "Account",
                balance = "100 000",
                currency = "RUB",
            ),
            category = Category(
                id = 3,
                name = "Ремонт квартиры",
                emoji = "РК",
                isIncome = false,
            ),
            amount = "100 000",
            transactionDate = "2025-06-13T10:22:43.162Z",
            comment = null,
            createdAt = "2025-06-13T10:22:49.297433Z",
            updatedAt = "2025-06-13T10:22:49.297433Z",
        ),
        TransactionResponse(
            id = 6,
            account = AccountBrief(
                id = 0,
                name = "Account",
                balance = "100 000",
                currency = "RUB",
            ),
            category = Category(
                id = 4,
                name = "Продукты",
                emoji = "\uD83C\uDF6D",
                isIncome = false,
            ),
            amount = "100 000",
            transactionDate = "2025-06-13T10:22:43.162Z",
            comment = null,
            createdAt = "2025-06-13T10:22:49.297433Z",
            updatedAt = "2025-06-13T10:22:49.297433Z",
        ),
        TransactionResponse(
            id = 7,
            account = AccountBrief(
                id = 0,
                name = "Account",
                balance = "100 000",
                currency = "RUB",
            ),
            category = Category(
                id = 5,
                name = "Спортзал",
                emoji = "\uD83C\uDFCB\uFE0F",
                isIncome = false,
            ),
            amount = "100 000",
            transactionDate = "2025-06-13T10:22:43.162Z",
            comment = null,
            createdAt = "2025-06-13T10:22:49.297433Z",
            updatedAt = "2025-06-13T10:22:49.297433Z",
        ),
        TransactionResponse(
            id = 8,
            account = AccountBrief(
                id = 0,
                name = "Account",
                balance = "100 000",
                currency = "RUB",
            ),
            category = Category(
                id = 6,
                name = "Медицина",
                emoji = "\uD83D\uDC8A",
                isIncome = false,
            ),
            amount = "100 000",
            transactionDate = "2025-06-13T10:22:43.162Z",
            comment = null,
            createdAt = "2025-06-13T10:22:49.297433Z",
            updatedAt = "2025-06-13T10:22:49.297433Z",
        ),
    )

    val total = ""
}