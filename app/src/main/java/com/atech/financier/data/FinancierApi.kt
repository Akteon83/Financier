package com.atech.financier.data

import com.atech.financier.domain.model.Account
import com.atech.financier.domain.model.TransactionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/** API для взаимодействия с сервером при помощи Retrofit */
interface FinancierApi {

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactions(
        @Header("Authorization") token: String = "Bearer 6JpN0O3FBKiO0MxjHtUVb3ST",
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null
    ): Response<List<TransactionResponse>>

    @GET("accounts")
    suspend fun getAccounts(): Response<List<Account>>

}
