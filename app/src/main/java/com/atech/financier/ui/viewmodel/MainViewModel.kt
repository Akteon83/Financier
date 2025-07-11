package com.atech.financier.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.financier.ui.util.EventBus
import kotlinx.coroutines.launch

/** WORK IN PROGRESS */
class MainViewModel : ViewModel() {

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
