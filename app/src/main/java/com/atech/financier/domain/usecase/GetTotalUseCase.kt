package com.atech.financier.domain.usecase

import com.atech.financier.domain.model.TransactionResponse
import kotlin.math.abs

/** Use case для вычисления общей суммы транзакций для конкретного типа транзакции */
object GetTotalUseCase {

    fun execute(
        transactions: List<TransactionResponse>,
        transactionType: TransactionType = TransactionType.ANY
    ): String {

        var total = 0L

        transactions.forEach {
            if (it.category.isIncome) {
                when (transactionType) {
                    TransactionType.ANY, TransactionType.REVENUE -> {
                        total += (it.amount.toDouble() * 100).toLong()
                    }

                    else -> {}
                }
            } else {
                when (transactionType) {
                    TransactionType.ANY, TransactionType.EXPENSE -> {
                        total -= (it.amount.toDouble() * 100).toLong()
                    }

                    else -> {}
                }
            }
        }

        if (transactionType == TransactionType.EXPENSE) total = -total

        val sign = if (total < 0) "-" else ""

        return "$sign${(abs(total) / 100)}.${abs(total) % 100}"
    }
}

enum class TransactionType {
    ANY, EXPENSE, REVENUE
}
