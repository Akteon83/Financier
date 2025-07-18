package com.atech.financier.data.repository

import android.widget.Toast
import com.atech.financier.FinancierApplication
import com.atech.financier.R
import com.atech.financier.data.local.RoomInstance
import com.atech.financier.data.remote.RetrofitInstance
import com.atech.financier.data.mapper.toDomain
import com.atech.financier.data.mapper.toDto
import com.atech.financier.data.mapper.toEntity
import com.atech.financier.data.remote.dto.TransactionResponseDto
import com.atech.financier.domain.model.Transaction
import com.atech.financier.domain.repository.TransactionRepository
import com.atech.financier.ui.util.ConnectionObserver
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
        if (requireUpdate || _transactions == null) {
            loadDatabase()
            loadTransactions(
                accountId = accountId,
                requireSync = true,
            )
        }
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

                val transaction = RetrofitInstance.api.createTransaction(
                    transaction = transaction.toDto(),
                ).body()

                if (transaction != null) {
                    RoomInstance.database.transactionDao
                        .upsert(transaction.toEntity())
                }
            } else {

                val transaction = RetrofitInstance.api.updateTransaction(
                    id = transaction.id,
                    transaction = transaction.toDto(),
                ).body()

                if (transaction != null) {
                    RoomInstance.database.transactionDao
                        .upsert(transaction.toEntity())
                }
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

            val response = RetrofitInstance.api.deleteTransaction(
                id = id,
            )

            if (response.isSuccessful) {
                RoomInstance.database.transactionDao.deleteById(id)
            }

            loadTransactions(1)
            EventBus.invokeAction(EventBus.GlobalAction.TransactionsUpdated)
        } catch (e: Exception) {

        }
    }

    suspend fun loadTransactions(
        accountId: Int,
        startDate: Instant? = null,
        endDate: Instant? = null,
        requireSync: Boolean = false,
    ) {
        try {
            if (ConnectionObserver.hasInternetAccess()) {

                Toast.makeText(
                    FinancierApplication.context,
                    R.string.loading_data,
                    Toast.LENGTH_SHORT
                ).show()

                val response = RetrofitInstance.api.getTransactions(
                    accountId = accountId,
                    startDate = if (startDate == null) null else DateTimeFormatter
                        .ISO_INSTANT.format(startDate),
                    endDate = if (endDate == null) null else DateTimeFormatter
                        .ISO_INSTANT.format(endDate),
                ).body() ?: emptyList()

                _transactions = response.map { it.toDomain() }.sortedByDescending { it.dateTime }
                if (requireSync) syncDatabase(response)
            } else {

                Toast.makeText(
                    FinancierApplication.context,
                    R.string.no_internet,
                    Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {

        }
    }

    private suspend fun loadDatabase() {

        _transactions = RoomInstance.database.transactionDao.getAll()
            .map { it.toDomain() }.sortedByDescending { it.dateTime }
    }

    private suspend fun syncDatabase(transactions: List<TransactionResponseDto>) {

        val localTransactions = RoomInstance.database.transactionDao
            .getAllBasic().map { it to false }.associateBy { it.first.id }.toMutableMap()

        transactions.forEach {
            val localTransaction = localTransactions.getOrDefault(it.id, null)
            if (localTransaction != null) {
                if (localTransaction.first.updatedAt != it.updatedAt) {
                    RoomInstance.database.transactionDao.upsert(it.toEntity())
                }
                localTransactions[it.id] = localTransaction.first to true
            } else {
                RoomInstance.database.transactionDao.upsert(it.toEntity())
            }
        }

        localTransactions.forEach {
            if (!it.value.second) {
                RoomInstance.database.transactionDao.delete(it.value.first)
            }
        }

        Toast.makeText(
            FinancierApplication.context,
            R.string.data_sync,
            Toast.LENGTH_SHORT
        ).show()
    }
}
