package com.atech.financier.domain.model

data class AccountHistory(
    val id: Int,
    val accountId: Int,
    val changeType: String,
    val previousState: AccountState,
    val newState: AccountState,
    val changeTimestamp: String,
    val createdAt: String,
)

data class AccountHistoryResponse(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: AccountHistory,
)
