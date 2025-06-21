package com.atech.financier.domain.model

data class Account(
    val id: Int,
    val userId: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val createdAt: String,
    val updatedAt: String,
)

data class AccountResponse(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
    val incomeStats: StatItem,
    val expenseStats: StatItem,
    val createdAt: String,
    val updatedAt: String,
)

data class AccountState(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
)

data class AccountBrief(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
)
