package com.atech.financier.data.remote.dto

data class AccountBriefDto(
    val id: Int,
    val name: String,
    val balance: String,
    val currency: String,
)
