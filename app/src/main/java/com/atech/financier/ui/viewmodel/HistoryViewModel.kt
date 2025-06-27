package com.atech.financier.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.financier.data.RetrofitInstance.api
import com.atech.financier.domain.usecase.GetTotalUseCase
import com.atech.financier.ui.util.MockData
import com.atech.financier.ui.util.asNumber
import com.atech.financier.ui.util.toExpenseItemState
import com.atech.financier.ui.util.toTransactionItemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Currency

class HistoryViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        HistoryState(
            beginning = "Февраль 2025",
            ending = "23:41"
        )
    )
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            try {
                val response = api.getTransactions(accountId = 1)
                _state.update { currentState ->
                    currentState.copy(
                        total = GetTotalUseCase.execute(response.body() ?: emptyList()).asNumber(),
                        currency = Currency.getInstance(
                            response.body()?.first()?.account?.currency ?: "USD"
                        ).symbol,
                        transactions = response.body()?.mapNotNull { it.toTransactionItemState() }
                            ?: emptyList()
                    )
                }
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
