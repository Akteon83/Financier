package com.atech.financier.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.financier.data.repository.AccountRepositoryImpl
import com.atech.financier.data.repository.CategoryRepositoryImpl
import com.atech.financier.data.repository.TransactionRepositoryImpl
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

class AnalysisViewModel : ViewModel() {

    private val _state = MutableStateFlow(AnalysisState())
    val state: StateFlow<AnalysisState> = _state.asStateFlow()

    private var isIncome = false

    fun revenuesAnalysis(isRevenuesAnalysis: Boolean) {
        isIncome = isRevenuesAnalysis
    }

    fun onAction(action: AnalysisAction) {
        when (action) {
            is AnalysisAction.ChangeStartDate -> onStartChange(action.date)
            is AnalysisAction.ChangeEndDate -> onEndChange(action.date)
            AnalysisAction.ShowStartPicker -> showStartPicker()
            AnalysisAction.HideStartPicker -> hideStartPicker()
            AnalysisAction.ShowEndPicker -> showEndPicker()
            AnalysisAction.HideEndPicker -> hideEndPicker()
        }
    }

    fun loadAnalysis(requireUpdate: Boolean = false) {

        viewModelScope.launch {

            val account = AccountRepositoryImpl
                .getAccount(
                    id = 1,
                    requireUpdate = requireUpdate,
                )

            val categories = CategoryRepositoryImpl
                .getCategories(
                    requireUpdate = requireUpdate,
                ).associateBy { it.id }

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
                            && it.amount != 0L
                }

            val analysisItems = hashMapOf<Int, Long>()

            transactions.forEach {
                analysisItems[it.categoryId] = analysisItems
                    .getOrDefault(it.categoryId, 0) + it.amount
            }

            val total = transactions.sumOf { it.amount }

            _state.update { currentState ->
                currentState.copy(
                    total = total.toAmount(),
                    currency = account?.currency?.toCurrencySymbol() ?: "#",
                    categories = buildList {
                        analysisItems.forEach { item ->
                            categories.getOrDefault(item.key, null)?.let {
                                add(
                                    AnalysisItemState(
                                        id = it.id,
                                        title = it.title,
                                        amount = item.value,
                                        emoji = it.emoji,
                                        share = if (total != 0L) {
                                            (item.value * 100 / total).toInt()
                                        } else 0
                                    )
                                )
                            }
                        }
                    }.sortedByDescending { it.amount }
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
        loadAnalysis()
    }

    private fun onEndChange(date: LocalDate?) {
        _state.update { currentState ->
            currentState.copy(
                endDate = date ?: currentState.endDate
            )
        }
        loadAnalysis()
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
data class AnalysisState(
    val startDate: LocalDate = LocalDate.now().withDayOfMonth(1),
    val endDate: LocalDate = LocalDate.now(),
    val total: String = "",
    val currency: String = "",
    val categories: List<AnalysisItemState> = emptyList(),
    val showStartPicker: Boolean = false,
    val showEndPicker: Boolean = false,
)

@Immutable
data class AnalysisItemState(
    val id: Int,
    val title: String = "",
    val amount: Long = 0L,
    val emoji: String = "",
    val share: Int = 0,
)

sealed interface AnalysisAction {
    data class ChangeStartDate(val date: LocalDate?) : AnalysisAction
    data class ChangeEndDate(val date: LocalDate?) : AnalysisAction
    object ShowStartPicker : AnalysisAction
    object HideStartPicker : AnalysisAction
    object ShowEndPicker : AnalysisAction
    object HideEndPicker : AnalysisAction
}
