package com.atech.financier.ui.util

import com.atech.financier.domain.model.TransactionResponse
import com.atech.financier.ui.viewmodel.ExpenseItemState
import com.atech.financier.ui.viewmodel.RevenueItemState
import com.atech.financier.ui.viewmodel.TransactionItemState

fun TransactionResponse.toExpenseItemState(): ExpenseItemState? {
    return if (this.category.isIncome) null
    else ExpenseItemState(
        title = this.category.name,
        amount = this.amount.asNumber(),
        description = this.comment ?: "",
        emoji = this.category.emoji
    )
}

fun TransactionResponse.toRevenueItemState() : RevenueItemState? {
    return if (!this.category.isIncome) null
    else RevenueItemState(
        title = this.category.name,
        amount = this.amount.asNumber(),
        description = this.comment ?: ""
    )
}

fun TransactionResponse.toTransactionItemState() : TransactionItemState {
    return TransactionItemState(
        title = this.category.name,
        amount = this.amount.asNumber(),
        description = this.comment ?: "",
        emoji = this.category.emoji,
        time = this.transactionDate.take(10)
    )
}
