package com.atech.financier.domain.model

data class Account(
    val id: Int,
    val name: String,
    val balance: Long,
    val currency: String,
)
