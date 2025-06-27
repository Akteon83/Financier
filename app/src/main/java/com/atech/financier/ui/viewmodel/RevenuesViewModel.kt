package com.atech.financier.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.financier.data.RetrofitInstance.api
import com.atech.financier.domain.usecase.GetTotalUseCase
import com.atech.financier.domain.usecase.TransactionType
import com.atech.financier.ui.util.asNumber
import com.atech.financier.ui.util.toRevenueItemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Currency

class RevenuesViewModel : ViewModel() {

    private val _state = MutableStateFlow(RevenuesState())
    val state: StateFlow<RevenuesState> = _state.asStateFlow()

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            try {
                val response = api.getTransactions(accountId = 1)
                _state.update { currentState ->
                    currentState.copy(
                        total = GetTotalUseCase.execute(
                            response.body() ?: emptyList(),
                            TransactionType.REVENUE
                        ).asNumber(),
                        currency = Currency.getInstance(
                            response.body()?.first()?.account?.currency ?: "USD"
                        ).symbol,
                        revenues = response.body()?.mapNotNull { it.toRevenueItemState() }
                            ?: emptyList()
                    )
                }
            } catch (e: Error) {

            }
        }
    }

}

@Immutable
data class RevenuesState(
    val total: String = "",
    val currency: String = "",
    val revenues: List<RevenueItemState> = emptyList(),
)

@Immutable
data class RevenueItemState(
    val title: String = "",
    val amount: String = "",
    val description: String = "",
)
