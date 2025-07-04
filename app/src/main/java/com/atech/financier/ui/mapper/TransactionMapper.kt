package com.atech.financier.ui.mapper

import com.atech.financier.domain.model.Transaction
import com.atech.financier.ui.util.toAmount
import com.atech.financier.ui.util.toDateTime
import com.atech.financier.ui.viewmodel.ExpenseItemState
import com.atech.financier.ui.viewmodel.HistoryItemState
import com.atech.financier.ui.viewmodel.RevenueItemState

fun Transaction.toExpenseItemState(): ExpenseItemState? {
    return if (amount >= 0) null
    else ExpenseItemState(
        title = title,
        amount = (-amount).toAmount(),
        description = comment ?: "",
        emoji = emoji,
    )
}

fun Transaction.toRevenueItemState(): RevenueItemState? {
    return if (amount < 0) null
    else RevenueItemState(
        title = title,
        amount = amount.toAmount(),
        description = comment ?: "",
    )
}

fun Transaction.toHistoryItemState(): HistoryItemState {
    return HistoryItemState(
        title = title,
        amount = amount.toAmount(),
        description = comment ?: "",
        emoji = emoji,
        dateTime = dateTime.toDateTime(),
    )
}
