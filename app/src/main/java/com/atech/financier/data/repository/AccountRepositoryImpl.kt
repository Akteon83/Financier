package com.atech.financier.data.repository

import com.atech.financier.data.local.RoomInstance
import com.atech.financier.data.remote.RetrofitInstance
import com.atech.financier.data.mapper.toDomain
import com.atech.financier.data.mapper.toDto
import com.atech.financier.data.mapper.toEntity
import com.atech.financier.data.remote.dto.AccountResponseDto
import com.atech.financier.domain.model.Account
import com.atech.financier.domain.repository.AccountRepository
import com.atech.financier.ui.util.ConnectionObserver
import com.atech.financier.ui.util.EventBus

object AccountRepositoryImpl : AccountRepository {

    private var _account: Account? = null

    override suspend fun getAccount(
        id: Int,
        requireUpdate: Boolean,
    ): Account? {
        if (requireUpdate || _account == null) loadAccount(
            id = id,
            requireSync = true,
            )
        return _account
    }

    override suspend fun updateAccount(
        account: Account,
    ) {
        try {

            val response = RetrofitInstance.api.updateAccount(
                id = account.id,
                account = account.toDto(),
            ).body()

            if (response != null) {
                RoomInstance.database.accountDao.upsertAccount(response.toEntity())
            }

            _account = response?.toDomain()
            EventBus.invokeAction(EventBus.GlobalAction.AccountUpdated)
        } catch (e: Exception) {

        }
    }

    suspend fun loadAccount(
        id: Int,
        requireSync: Boolean = false,
    ) {
        try {
            if (ConnectionObserver.hasInternetAccess()) {

                _account = RoomInstance.database.accountDao.getById(1).toDomain()

                val response = RetrofitInstance.api.getAccount(
                    id = id,
                ).body()

                _account = response?.toDomain()
                if (requireSync && response != null) syncDatabase(response)
            } else {

                _account = RoomInstance.database.accountDao.getById(1).toDomain()
            }
        } catch (e: Exception) {

        }
    }

    suspend fun syncDatabase(account: AccountResponseDto) {
        RoomInstance.database.accountDao.upsertAccount(account.toEntity())
    }
}
