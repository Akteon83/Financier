package com.atech.financier.data.remote.dto

data class CategoryDto(
    val id: Int,
    val name: String,
    val emoji: String,
    val isIncome: Boolean,
)
