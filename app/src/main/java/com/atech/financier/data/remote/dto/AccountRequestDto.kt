package com.atech.financier.data.remote.dto

data class AccountRequestDto(
    val name: String,
    val balance: String,
    val currency: String,
)
