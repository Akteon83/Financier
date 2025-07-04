package com.atech.financier.domain.model

import java.time.Instant

data class Transaction(
    val id: Int,
    val amount: Long,
    val title: String,
    val emoji: String,
    val comment: String?,
    val dateTime: Instant,
)
