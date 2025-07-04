package com.atech.financier.ui.viewmodel

import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.financier.data.repository.AccountRepositoryImpl
import com.atech.financier.domain.model.Account
import com.atech.financier.ui.util.EventBus
import com.atech.financier.ui.util.toAmount
import com.atech.financier.ui.util.toBalance
import com.atech.financier.ui.util.toCurrencyString
import com.atech.financier.ui.util.toCurrencySymbol
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AccountEditorViewModel : ViewModel() {

    private val _state = MutableStateFlow(AccountEditorState())
    val state: StateFlow<AccountEditorState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            EventBus.actions.collect {
                if (it is EventBus.GlobalAction.UpdateAccount) updateAccount()
            }
        }
    }

    fun onNameChange(name: String) {
        _state.update { currentState ->
            currentState.copy(
                name = name
            )
        }
    }

    fun onBalanceChange(balance: String) {
        _state.update { currentState ->
            currentState.copy(
                balance = balance
            )
        }
    }

    fun onCurrencyChange(currency: String) {
        _state.update { currentState ->
            currentState.copy(
                currency = currency
            )
        }
    }

    fun showBottomSheet() {
        _state.update { currentState ->
            currentState.copy(
                showBottomSheet = true
            )
        }
    }

    fun hideBottomSheet() {
        _state.update { currentState ->
            currentState.copy(
                showBottomSheet = false
            )
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
                    name = account?.name ?: "UNKNOWN",
                    balance = account?.balance?.toAmount() ?: "?",
                    currency = account?.currency?.toCurrencySymbol() ?: "#",
                )
            }
        }
    }

    private fun updateAccount() {
        viewModelScope.launch {
            withContext(NonCancellable) {
                val account = Account(
                    id = 1,
                    name = state.value.name,
                    balance = state.value.balance.toBalance(),
                    currency = state.value.currency.toCurrencyString(),
                )
                AccountRepositoryImpl.updateAccount(
                    account = account,
                )
            }
        }
    }

}

@Immutable
data class AccountEditorState(
    val name: String = "",
    val balance: String = "",
    val currency: String = "",
    val showBottomSheet: Boolean = false,
)
