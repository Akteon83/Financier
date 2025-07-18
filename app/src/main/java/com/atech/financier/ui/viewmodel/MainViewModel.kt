package com.atech.financier.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.financier.data.repository.AccountRepositoryImpl
import com.atech.financier.data.repository.CategoryRepositoryImpl
import com.atech.financier.data.repository.TransactionRepositoryImpl
import com.atech.financier.ui.util.ConnectionObserver
import com.atech.financier.ui.util.EventBus
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    /*init {
        viewModelScope.launch {

            AccountRepositoryImpl.loadAccount(
                id = 1,
                requireSync = ConnectionObserver.hasInternetAccess()
            )

            CategoryRepositoryImpl.loadCategories(
                requireSync = ConnectionObserver.hasInternetAccess()
            )

            TransactionRepositoryImpl.loadTransactions(
                accountId = 1,
                requireSync = ConnectionObserver.hasInternetAccess()
            )
        }
    }*/

    fun updateAccount() {
        viewModelScope.launch {
            EventBus.invokeAction(EventBus.GlobalAction.UpdateAccount)
        }
    }

    fun updateTransaction() {
        viewModelScope.launch {
            EventBus.invokeAction(EventBus.GlobalAction.UpdateTransaction)
        }
    }
}
