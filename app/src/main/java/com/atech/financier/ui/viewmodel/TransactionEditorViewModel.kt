package com.atech.financier.ui.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Immutable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.atech.financier.data.repository.AccountRepositoryImpl
import com.atech.financier.data.repository.CategoryRepositoryImpl
import com.atech.financier.data.repository.TransactionRepositoryImpl
import com.atech.financier.domain.model.Transaction
import com.atech.financier.ui.mapper.toCategoryItemState
import com.atech.financier.ui.util.EventBus
import com.atech.financier.ui.util.toAmount
import com.atech.financier.ui.util.toBalance
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset

class TransactionEditorViewModel : ViewModel() {

    private val _state = MutableStateFlow(TransactionEditorState())
    val state: StateFlow<TransactionEditorState> = _state.asStateFlow()

    private var isIncome = false
    private var transactionId = -1
    private var category: CategoryItemState? = null

    init {
        viewModelScope.launch {
            EventBus.actions.collect {
                if (it is EventBus.GlobalAction.UpdateTransaction) updateTransaction()
            }
        }
    }

    fun setTransactionInfo(isIncome: Boolean, id: Int) {
        this.isIncome = isIncome
        transactionId = id
    }

    fun onCategoryChange(category: CategoryItemState) {
        this.category = category
        _state.update { currentState ->
            currentState.copy(
                categoryTitle = category.title
            )
        }
    }

    fun onAmountChange(amount: String) {
        _state.update { currentState ->
            currentState.copy(
                amount = amount
            )
        }
    }

    fun onTitleChange(title: String) {
        _state.update { currentState ->
            currentState.copy(
                title = title
            )
        }
    }

    fun onDateChange(date: Long?) {
        _state.update { currentState ->
            currentState.copy(
                date = if (date != null) {
                    Instant.ofEpochMilli(date).atZone(ZoneId.systemDefault()).toLocalDate()
                } else {
                    Log.i(null, "Bleeding Me...")
                    currentState.date
                }
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    fun onTimeChange(time: LocalTime) {
        _state.update { currentState ->
            currentState.copy(
                time = time
            )
        }
    }

    fun showDatePicker() {
        _state.update { currentState ->
            currentState.copy(
                showDatePicker = true
            )
        }
    }

    fun hideDatePicker() {
        _state.update { currentState ->
            currentState.copy(
                showDatePicker = false
            )
        }
    }

    fun showTimePicker() {
        _state.update { currentState ->
            currentState.copy(
                showTimePicker = true
            )
        }
    }

    fun hideTimePicker() {
        _state.update { currentState ->
            currentState.copy(
                showTimePicker = false
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

    fun loadCategories() {

        viewModelScope.launch {

            val categories = CategoryRepositoryImpl.getCategories(requireUpdate = false)
            _state.update { currentState ->
                currentState.copy(
                    categories = categories.filter { it.isIncome == isIncome }
                        .map { it.toCategoryItemState() }
                )
            }
            if (category == null) onCategoryChange(state.value.categories.first())
        }
    }

    fun loadAccount() {

        viewModelScope.launch {

            val account = AccountRepositoryImpl
                .getAccount(
                    id = 1,
                )

            _state.update { currentState ->
                currentState.copy(
                    accountName = account?.name ?: "UNKNOWN"
                )
            }
        }
    }

    fun loadTransaction() {

        viewModelScope.launch {

            val transaction = TransactionRepositoryImpl
                .getTransaction(
                    id = transactionId,
                )

            val account = AccountRepositoryImpl
                .getAccount(
                    id = transaction?.accountId ?: 1,
                )

            _state.update { currentState ->
                currentState.copy(
                    accountName = account?.name ?: "UNKNOWN",
                    categoryTitle = transaction?.title ?: "UNKNOWN",
                    amount = transaction?.amount?.toAmount() ?: "0",
                    date = transaction?.dateTime?.atZone(ZoneId.systemDefault())
                        ?.toLocalDate() ?: currentState.date,
                    time = transaction?.dateTime?.atZone(ZoneId.systemDefault())
                        ?.toLocalTime() ?: currentState.time,
                    title = transaction?.comment ?: "-"
                )
            }
        }
    }

    fun updateTransaction() {

        viewModelScope.launch {

            withContext(NonCancellable) {

                TransactionRepositoryImpl.updateTransaction(
                    Transaction(
                        id = transactionId,
                        accountId = 1,
                        categoryId = category?.id ?: 0,
                        amount = state.value.amount.toBalance(),
                        title = category?.title ?: "",
                        emoji = category?.emoji ?: "",
                        comment = state.value.title,
                        dateTime = LocalDateTime.of(state.value.date, state.value.time)
                            .atZone(ZoneId.systemDefault()).toInstant(),
                        isIncome = isIncome,
                    )
                )
            }
        }
    }

    fun deleteTransaction() {
        if (transactionId != -1) {
            viewModelScope.launch {
                withContext(NonCancellable) {
                    TransactionRepositoryImpl.deleteTransaction(transactionId)
                }
            }
        }
    }
}

@Immutable
data class TransactionEditorState(
    val accountName: String = "",
    val categoryTitle: String = "",
    val amount: String = "",
    val date: LocalDate = LocalDate.now(),
    val time: LocalTime = LocalTime.now(),
    val title: String = "",
    val categories: List<CategoryItemState> = emptyList(),
    val showDatePicker: Boolean = false,
    val showTimePicker: Boolean = false,
    val showBottomSheet: Boolean = false,
)
