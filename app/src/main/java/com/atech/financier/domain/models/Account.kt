package com.atech.financier.domain.models

data class Account(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String
)

data class AccountCreateRequest(
    val name: String,
    val balance: String,
    val currency: String
)

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String
)

data class AccountResponse(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: StatItem,
    val expenseStats: StatItem,
    val createdAt: String,
    val updatedAt: String
)

data class AccountState(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)

data class AccountHistory(
    val id: Int,
    val accountId: Int,
    val changeType: String,
    val previousState: AccountState,
    val newState: AccountState,
    val changeTimestamp: String,
    val createdAt: String
)

data class AccountHistoryResponse(
    val accountId: Int,
    val accountName: String,
    val currency: String,
    val currentBalance: String,
    val history: AccountHistory
)

data class AccountBrief(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String
)

enum class ChangeType {
    CREATION, MODIFICATION
}