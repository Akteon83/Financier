package com.atech.financier.domain.repository

import com.atech.financier.domain.model.Transaction
import java.time.Instant

interface TransactionRepository {

    suspend fun getTransactions(
        accountId: Int,
        startDate: Instant? = null,
        endDate: Instant? = null,
        requireUpdate: Boolean = false,
    ): List<Transaction>

    suspend fun getTransaction(
        id: Int,
    ): Transaction?

    suspend fun updateTransaction(
        transaction: Transaction,
    )

    suspend fun deleteTransaction(
        id: Int,
    )
}
