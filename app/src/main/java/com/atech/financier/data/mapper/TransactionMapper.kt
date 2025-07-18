package com.atech.financier.data.mapper

import com.atech.financier.data.local.entity.TransactionEntity
import com.atech.financier.data.local.relation.TransactionWithCategory
import com.atech.financier.data.remote.dto.TransactionDto
import com.atech.financier.data.remote.dto.TransactionRequestDto
import com.atech.financier.data.remote.dto.TransactionResponseDto
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

fun TransactionWithCategory.toDomain() = Transaction(
    id = transaction.id,
    accountId = transaction.accountId,
    categoryId = transaction.categoryId,
    amount = transaction.amount,
    title = category.name,
    emoji = category.emoji,
    comment = transaction.comment,
    dateTime = Instant.parse(transaction.transactionDate),
    isIncome = category.isIncome,
)


fun Transaction.toDto() = TransactionRequestDto(
    accountId = accountId,
    categoryId = categoryId,
    amount = "${amount.toString().dropLast(2)}.${("00$amount").takeLast(2)}",
    transactionDate = dateTime.toString(),
    comment = comment ?: "",
)

fun TransactionDto.toEntity() = TransactionEntity(
    id = id,
    accountId = accountId,
    categoryId = categoryId,
    amount = amount.filter { it.isDigit() }.toLong(),
    transactionDate = transactionDate,
    comment = comment,
    createdAt = createdAt,
    updatedAt = updatedAt,
)

fun TransactionResponseDto.toEntity() = TransactionEntity(
    id = id,
    accountId = account.id,
    categoryId = category.id,
    amount = amount.filter { it.isDigit() }.toLong(),
    transactionDate = transactionDate,
    comment = comment,
    createdAt = createdAt,
    updatedAt = updatedAt,
)
