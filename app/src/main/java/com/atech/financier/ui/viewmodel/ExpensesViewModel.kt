package com.atech.financier.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.financier.data.repository.AccountRepositoryImpl
import com.atech.financier.data.repository.TransactionRepositoryImpl
import com.atech.financier.ui.mapper.toExpenseItemState
import com.atech.financier.ui.util.EventBus
import com.atech.financier.ui.util.toAmount
import com.atech.financier.ui.util.toCurrencySymbol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ExpensesViewModel : ViewModel() {

    private val _state = MutableStateFlow(ExpensesState())
    val state: StateFlow<ExpensesState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            EventBus.actions.collect {
                if (it is EventBus.GlobalAction.TransactionsUpdated) loadTransactions()
            }
        }
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
                ).filter { !it.isIncome }

            _state.update { currentState ->
                currentState.copy(
                    total = transactions.sumOf { it.amount }.toAmount(),
                    currency = account?.currency?.toCurrencySymbol() ?: "#",
                    expenses = transactions.mapNotNull { it.toExpenseItemState() }
                )
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
    val id: Int,
    val title: String = "",
    val amount: String = "",
    val description: String = "",
    val emoji: String = "",
)
