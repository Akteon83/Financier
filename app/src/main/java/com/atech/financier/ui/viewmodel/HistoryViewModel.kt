package com.atech.financier.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.financier.data.repository.AccountRepositoryImpl
import com.atech.financier.data.repository.TransactionRepositoryImpl
import com.atech.financier.ui.mapper.toHistoryItemState
import com.atech.financier.ui.util.toAmount
import com.atech.financier.ui.util.toCurrencySymbol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HistoryViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        HistoryState(
            beginning = "Февраль 2025",
            ending = "23:41"
        )
    )
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    private var isIncome = false

    fun revenuesHistory(isRevenuesHistory: Boolean) {
        isIncome = isRevenuesHistory
    }

    fun updateTransactions() = loadTransactions(true)

    fun loadTransactions(requireUpdate: Boolean = false) {

        viewModelScope.launch {

            val account = AccountRepositoryImpl
                .getAccount(
                    id = 1,
                    requireUpdate = requireUpdate,
                )

            val transactions = TransactionRepositoryImpl
                .getTransactions(
                    accountId = 1,
                    requireUpdate = requireUpdate,
                ).filter { it.isIncome == isIncome }.sortedByDescending { it.dateTime }

            _state.update { currentState ->
                currentState.copy(
                    total = transactions.sumOf { it.amount }.toAmount(),
                    currency = account?.currency?.toCurrencySymbol() ?: "#",
                    transactions = transactions.map { it.toHistoryItemState() }
                )
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
    val transactions: List<HistoryItemState> = emptyList(),
)

@Immutable
data class HistoryItemState(
    val id: Int,
    val title: String = "",
    val amount: String = "",
    val description: String = "",
    val emoji: String = "",
    val dateTime: String = "",
)
