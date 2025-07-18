package com.atech.financier.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.atech.financier.data.local.entity.TransactionEntity
import com.atech.financier.data.local.relation.TransactionWithCategory

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transactions")
    suspend fun getAllBasic(): List<TransactionEntity>

    @Transaction
    @Query("SELECT * FROM transactions")
    suspend fun getAll(): List<TransactionWithCategory>

    @Transaction
    @Query("SELECT * FROM transactions WHERE id = :id")
    suspend fun getById(id: Int): TransactionWithCategory

    @Query("DELETE FROM transactions WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Upsert
    suspend fun upsert(transactionEntity: TransactionEntity)

    @Delete
    suspend fun delete(transactionEntity: TransactionEntity)
}
