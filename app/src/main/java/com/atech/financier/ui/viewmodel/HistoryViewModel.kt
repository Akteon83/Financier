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
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId

class HistoryViewModel : ViewModel() {

    private val _state = MutableStateFlow(HistoryState())
    val state: StateFlow<HistoryState> = _state.asStateFlow()

    private var isIncome = false

    fun revenuesHistory(isRevenuesHistory: Boolean) {
        isIncome = isRevenuesHistory
    }

    fun onAction(action: HistoryAction) {
        when (action) {
            is HistoryAction.ChangeStartDate -> onStartChange(action.date)
            is HistoryAction.ChangeEndDate -> onEndChange(action.date)
            HistoryAction.ShowStartPicker -> showStartPicker()
            HistoryAction.HideStartPicker -> hideStartPicker()
            HistoryAction.ShowEndPicker -> showEndPicker()
            HistoryAction.HideEndPicker -> hideEndPicker()
            HistoryAction.UpdateTransactions -> loadTransactions(true)
        }
    }

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
                ).filter {
                    it.isIncome == isIncome
                            && LocalDateTime.of(state.value.startDate, LocalTime.MIN)
                        .atZone(ZoneId.systemDefault()).toInstant() < it.dateTime
                            && LocalDateTime.of(state.value.endDate, LocalTime.MAX)
                        .atZone(ZoneId.systemDefault()).toInstant() > it.dateTime
                }.sortedByDescending { it.dateTime }

            _state.update { currentState ->
                currentState.copy(
                    total = transactions.sumOf { it.amount }.toAmount(),
                    currency = account?.currency?.toCurrencySymbol() ?: "#",
                    transactions = transactions.map { it.toHistoryItemState() }
                )
            }
        }
    }

    private fun onStartChange(date: LocalDate?) {
        _state.update { currentState ->
            currentState.copy(
                startDate = date ?: currentState.startDate
            )
        }
        loadTransactions()
    }

    private fun onEndChange(date: LocalDate?) {
        _state.update { currentState ->
            currentState.copy(
                endDate = date ?: currentState.endDate
            )
        }
        loadTransactions()
    }

    private fun showStartPicker() {
        _state.update { currentState ->
            currentState.copy(
                showStartPicker = true
            )
        }
    }

    private fun hideStartPicker() {
        _state.update { currentState ->
            currentState.copy(
                showStartPicker = false
            )
        }
    }

    private fun showEndPicker() {
        _state.update { currentState ->
            currentState.copy(
                showEndPicker = true
            )
        }
    }

    private fun hideEndPicker() {
        _state.update { currentState ->
            currentState.copy(
                showEndPicker = false
            )
        }
    }
}

@Immutable
data class HistoryState(
    val startDate: LocalDate = LocalDate.now().withDayOfMonth(1),
    val endDate: LocalDate = LocalDate.now(),
    val total: String = "",
    val currency: String = "",
    val transactions: List<HistoryItemState> = emptyList(),
    val showStartPicker: Boolean = false,
    val showEndPicker: Boolean = false,
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

sealed interface HistoryAction {
    data class ChangeStartDate(val date: LocalDate?) : HistoryAction
    data class ChangeEndDate(val date: LocalDate?) : HistoryAction
    object ShowStartPicker : HistoryAction
    object HideStartPicker : HistoryAction
    object ShowEndPicker : HistoryAction
    object HideEndPicker : HistoryAction
    object UpdateTransactions : HistoryAction
}
