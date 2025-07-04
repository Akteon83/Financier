package com.atech.financier.data.repository

import com.atech.financier.data.RetrofitInstance
import com.atech.financier.data.mapper.toDomain
import com.atech.financier.domain.model.Transaction
import com.atech.financier.domain.repository.TransactionRepository
import java.time.Instant
import java.time.format.DateTimeFormatter

object TransactionRepositoryImpl : TransactionRepository {

    private var _transactions: List<Transaction>? = null

    override suspend fun getTransactions(
        accountId: Int,
        startDate: Instant?,
        endDate: Instant?,
        requireUpdate: Boolean,
    ): List<Transaction> {
        if (requireUpdate || _transactions == null) loadTransactions(accountId = accountId)
        return _transactions?.filter {
            (startDate == null || it.dateTime > startDate)
                    && (startDate == null || it.dateTime < endDate)
        } ?: emptyList()
    }

    suspend fun loadTransactions(
        accountId: Int,
        startDate: Instant? = null,
        endDate: Instant? = null,
    ) {
        try {
            _transactions = RetrofitInstance.api.getTransactions(
                accountId = accountId,
                startDate = if (startDate == null) null else DateTimeFormatter
                    .ISO_INSTANT.format(startDate),
                endDate = if (endDate == null) null else DateTimeFormatter
                    .ISO_INSTANT.format(endDate),
            ).body()?.map { it.toDomain() }?.sortedByDescending { it.dateTime } ?: emptyList()
        } catch (e: Error) {

        }
    }
}
