package com.atech.financier.domain.usecase

import com.atech.financier.domain.model.TransactionResponse

object GetTotalUseCase {

    fun execute(transactions: List<TransactionResponse>, transactionType: TransactionType = TransactionType.ANY): String {
        var total = 0L
        when (transactionType) {
            TransactionType.ANY -> {
                transactions.forEach {
                    total += (it.amount.toDouble() * 100).toLong()
                }
            }

            TransactionType.EXPENSE -> {
                transactions.forEach {
                    if (!it.category.isIncome) {
                        total += (it.amount.toDouble() * 100).toLong()
                    }
                }
            }

            TransactionType.REVENUE -> {
                transactions.forEach {
                    if (it.category.isIncome) {
                        total += (it.amount.toDouble() * 100).toLong()
                    }
                }
            }
        }
        return "${(total / 100)}.${total % 100}"
    }
}

enum class TransactionType {
    ANY, EXPENSE, REVENUE
}
