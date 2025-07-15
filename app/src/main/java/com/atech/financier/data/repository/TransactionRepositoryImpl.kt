package com.atech.financier.data.repository

import com.atech.financier.data.RetrofitInstance
import com.atech.financier.data.mapper.toDomain
import com.atech.financier.data.mapper.toDto
import com.atech.financier.domain.model.Transaction
import com.atech.financier.domain.repository.TransactionRepository
import com.atech.financier.ui.util.EventBus
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

    override suspend fun getTransaction(
        id: Int,
    ): Transaction? {
        return try {
            RetrofitInstance.api.getTransaction(
                id = id,
            ).body()?.toDomain()
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun updateTransaction(
        transaction: Transaction,
    ) {
        try {
            if (transaction.id == -1) {
                RetrofitInstance.api.createTransaction(
                    transaction = transaction.toDto(),
                )
            }
            else {
                RetrofitInstance.api.updateTransaction(
                    id = transaction.id,
                    transaction = transaction.toDto(),
                )
            }
            loadTransactions(transaction.accountId)
            EventBus.invokeAction(EventBus.GlobalAction.TransactionsUpdated)
        } catch (e: Exception) {

        }
    }

    override suspend fun deleteTransaction(
        id: Int,
    ) {
        try {
            RetrofitInstance.api.deleteTransaction(
                id = id,
            )
            loadTransactions(1)
            EventBus.invokeAction(EventBus.GlobalAction.TransactionsUpdated)
        } catch (e: Exception) {

        }
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
        } catch (e: Exception) {

        }
    }
}
