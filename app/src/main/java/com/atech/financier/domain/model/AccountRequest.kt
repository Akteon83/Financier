package com.atech.financier.domain.model

data class AccountCreateRequest(
    val name: String,
    val balance: String,
    val currency: String,
)

data class AccountUpdateRequest(
    val name: String,
    val balance: String,
    val currency: String,
)
