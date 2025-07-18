package com.atech.financier.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.atech.financier.data.local.entity.CategoryEntity
import com.atech.financier.data.local.entity.TransactionEntity

data class TransactionWithCategory(
    @Embedded val transaction: TransactionEntity,
    @Relation(
        parentColumn = "category_id",
        entityColumn = "id"
    )
    val category: CategoryEntity
)
