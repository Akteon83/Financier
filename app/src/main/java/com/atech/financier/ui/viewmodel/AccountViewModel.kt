package com.atech.financier.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.financier.data.repository.AccountRepositoryImpl
import com.atech.financier.ui.util.EventBus
import com.atech.financier.ui.util.toAmount
import com.atech.financier.ui.util.toCurrencySymbol
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AccountViewModel : ViewModel() {

    private val _state = MutableStateFlow(AccountState())
    val state: StateFlow<AccountState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            EventBus.actions.collect {
                if (it is EventBus.GlobalAction.AccountUpdated) loadAccount()
            }
        }
    }

    fun loadAccount(requireUpdate: Boolean = false) {
        viewModelScope.launch {
            val account = AccountRepositoryImpl
                .getAccount(
                    id = 1,
                    requireUpdate = requireUpdate,
                )
            _state.update { currentState ->
                currentState.copy(
                    balance = account?.balance?.toAmount() ?: "?",
                    currency = account?.currency?.toCurrencySymbol() ?: "#"
                )
            }
        }
    }

}

@Immutable
data class AccountState(
    val balance: String = "",
    val currency: String = "",
)
