package com.atech.financier.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.financier.data.RetrofitInstance.api
import com.atech.financier.domain.usecase.GetTotalUseCase
import com.atech.financier.domain.usecase.TransactionType
import com.atech.financier.ui.util.asNumber
import com.atech.financier.ui.util.toExpenseItemState
import kotlinx.coroutines.launch
import java.util.Currency

class ExpensesViewModel : ViewModel() {

    var state by mutableStateOf(
        ExpensesState(
            total = "800 000",
            currency = "$",
            expenses = listOf(

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
            ),
        )
    )
        private set

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            try {
                val response = api.getTransactions(accountId = 1)
                state = state.copy(
                    total = GetTotalUseCase.execute(response.body() ?: emptyList(), TransactionType.EXPENSE).asNumber(),
                    currency = Currency.getInstance(response.body()?.first()?.account?.currency ?: "USD").symbol,
                    expenses = response.body()?.mapNotNull { it.toExpenseItemState() }
                        ?: emptyList()
                )
            } catch (e: Error) {

            }
        }
    }

}

@Immutable
data class ExpensesState(
    val total: String = "",
    val currency: String = "",
    val expenses: List<ExpenseItemState> = emptyList(),
)

@Immutable
data class ExpenseItemState(
    val title: String = "",
    val amount: String = "",
    val description: String = "",
    val emoji: String = "",
)
