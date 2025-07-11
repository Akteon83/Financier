package com.atech.financier.data.repository

import com.atech.financier.data.RetrofitInstance
import com.atech.financier.data.mapper.toDomain
import com.atech.financier.data.mapper.toDto
import com.atech.financier.domain.model.Account
import com.atech.financier.domain.repository.AccountRepository
import com.atech.financier.ui.util.EventBus

object AccountRepositoryImpl : AccountRepository {

    private var _account: Account? = null

    override suspend fun getAccount(
        id: Int,
        requireUpdate: Boolean,
    ): Account? {
        if (requireUpdate || _account == null) loadAccount(id = id)
        return _account
    }

    override suspend fun updateAccount(
        account: Account,
    ) {
        try {
            _account = RetrofitInstance.api.updateAccount(
                id = account.id,
                account = account.toDto(),
            ).body()?.toDomain()
            EventBus.invokeAction(EventBus.GlobalAction.AccountUpdated)
        } catch (e: Exception) {

        }
    }

    suspend fun loadAccount(
        id: Int,
    ) {
        try {
            _account = RetrofitInstance.api.getAccount(
                id = id,
            ).body()?.toDomain()
        } catch (e: Exception) {

        }
    }
}
