package com.atech.financier.domain.repository

import com.atech.financier.domain.model.Account

interface AccountRepository {

    suspend fun getAccount(
        id: Int,
        requireUpdate: Boolean = false,
    ): Account?

    suspend fun updateAccount(
        account: Account,
    )
}