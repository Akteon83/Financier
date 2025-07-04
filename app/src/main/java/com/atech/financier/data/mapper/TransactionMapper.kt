package com.atech.financier.data.mapper

import com.atech.financier.data.dto.TransactionResponseDto
import com.atech.financier.domain.model.Transaction
import java.time.Instant

fun TransactionResponseDto.toDomain() = Transaction(
    id = id,
    amount = amount.filter { it.isDigit() }.toLong() * if (category.isIncome) 1 else -1,
    title = category.name,
    emoji = category.emoji,
    comment = comment,
    dateTime = Instant.parse(transactionDate),
)
