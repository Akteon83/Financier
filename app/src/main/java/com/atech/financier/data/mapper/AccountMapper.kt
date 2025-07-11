package com.atech.financier.data.mapper

import com.atech.financier.data.dto.AccountDto
import com.atech.financier.data.dto.AccountRequestDto
import com.atech.financier.data.dto.AccountResponseDto
import com.atech.financier.domain.model.Account

fun AccountDto.toDomain() = Account(
    id = id,
    name = name,
    balance = balance.filter { it.isDigit() }.toLong(),
    currency = currency,
)

fun AccountResponseDto.toDomain() = Account(
    id = id,
    name = name,
    balance = balance.filter { it.isDigit() }.toLong(),
    currency = currency,
)

fun Account.toDto() = AccountRequestDto(
    name = name,
    balance = "${balance.toString().dropLast(2)}.${("00$balance").takeLast(2)}",
    currency = currency
)
