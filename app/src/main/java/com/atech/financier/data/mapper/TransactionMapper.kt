package com.atech.financier.data.mapper

import com.atech.financier.data.dto.TransactionRequestDto
import com.atech.financier.data.dto.TransactionResponseDto
import com.atech.financier.domain.model.Transaction
import java.time.Instant

fun TransactionResponseDto.toDomain() = Transaction(
    id = id,
    accountId = account.id,
    categoryId = category.id,
    amount = amount.filter { it.isDigit() }.toLong(),
    title = category.name,
    emoji = category.emoji,
    comment = comment,
    dateTime = Instant.parse(transactionDate),
    isIncome = category.isIncome,
)

fun Transaction.toDto() = TransactionRequestDto(
    accountId = accountId,
    categoryId = categoryId,
    amount = "${amount.toString().dropLast(2)}.${("00$amount").takeLast(2)}",
    transactionDate = dateTime.toString(),
    comment = comment ?: "",
)
