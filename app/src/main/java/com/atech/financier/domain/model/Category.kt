package com.atech.financier.domain.model

data class Category(
    val id: Int,
    val title: String,
    val emoji: String,
    val isIncome: Boolean,
)
