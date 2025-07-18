package com.atech.financier.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.atech.financier.data.local.dao.AccountDao
import com.atech.financier.data.local.dao.CategoryDao
import com.atech.financier.data.local.dao.TransactionDao
import com.atech.financier.data.local.entity.AccountEntity
import com.atech.financier.data.local.entity.CategoryEntity
import com.atech.financier.data.local.entity.TransactionEntity

@Database(
    version = 1,
    entities = [
        TransactionEntity::class,
        CategoryEntity::class,
        AccountEntity::class,
    ]
)
abstract class FinancierDatabase : RoomDatabase() {

    abstract val transactionDao: TransactionDao

    abstract val categoryDao: CategoryDao

    abstract val accountDao: AccountDao
}
