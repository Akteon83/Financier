package com.atech.financier.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.financier.data.RetrofitInstance.api
import com.atech.financier.domain.usecase.GetTotalUseCase
import com.atech.financier.ui.util.asNumber
import com.atech.financier.ui.util.toExpenseItemState
import com.atech.financier.ui.util.toTransactionItemState
import kotlinx.coroutines.launch
import java.util.Currency

class HistoryViewModel : ViewModel() {

    var state by mutableStateOf(
        HistoryState(
            beginning = "Февраль 2025",
            ending = "23:41",
            total = "500 000",
            currency = "$",
            transactions = listOf(

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
                    total = GetTotalUseCase.execute(response.body() ?: emptyList()).asNumber(),
                    currency = Currency.getInstance(response.body()?.first()?.account?.currency ?: "USD").symbol,
                    transactions = response.body()?.mapNotNull { it.toTransactionItemState() }
                        ?: emptyList()
                )
            } catch (e: Error) {

            }
        }
    }

}

@Immutable
data class HistoryState(
    val beginning: String = "",
    val ending: String = "",
    val total: String = "",
    val currency: String = "",
    val transactions: List<TransactionItemState> = emptyList(),
)

@Immutable
data class TransactionItemState(
    val title: String = "",
    val amount: String = "",
    val description: String = "",
    val emoji: String = "",
    val time: String = "",
)
