package com.atech.financier.data.remote

import com.atech.financier.data.remote.dto.AccountDto
import com.atech.financier.data.remote.dto.AccountRequestDto
import com.atech.financier.data.remote.dto.AccountResponseDto
import com.atech.financier.data.remote.dto.CategoryDto
import com.atech.financier.data.remote.dto.TransactionDto
import com.atech.financier.data.remote.dto.TransactionRequestDto
import com.atech.financier.data.remote.dto.TransactionResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

/** API взаимодействия с сервером при помощи Retrofit */
interface FinancierApi {

    @GET("transactions/account/{accountId}/period")
    suspend fun getTransactions(
        @Header("Authorization") token: String = "Bearer 6JpN0O3FBKiO0MxjHtUVb3ST",
        @Path("accountId") accountId: Int,
        @Query("startDate") startDate: String? = null,
        @Query("endDate") endDate: String? = null,
    ): Response<List<TransactionResponseDto>>

    @GET("transactions/{id}")
    suspend fun getTransaction(
        @Header("Authorization") token: String = "Bearer 6JpN0O3FBKiO0MxjHtUVb3ST",
        @Path("id") id: Int,
    ): Response<TransactionResponseDto>

    @GET("accounts/{id}")
    suspend fun getAccount(
        @Header("Authorization") token: String = "Bearer 6JpN0O3FBKiO0MxjHtUVb3ST",
        @Path("id") id: Int,
    ): Response<AccountResponseDto>

    @GET("categories")
    suspend fun getCategories(
        @Header("Authorization") token: String = "Bearer 6JpN0O3FBKiO0MxjHtUVb3ST",
    ): Response<List<CategoryDto>>

    @POST("transactions")
    suspend fun createTransaction(
        @Header("Authorization") token: String = "Bearer 6JpN0O3FBKiO0MxjHtUVb3ST",
        @Body transaction: TransactionRequestDto,
    ): Response<TransactionDto>

    @PUT("transactions/{id}")
    suspend fun updateTransaction(
        @Header("Authorization") token: String = "Bearer 6JpN0O3FBKiO0MxjHtUVb3ST",
        @Path("id") id: Int,
        @Body transaction: TransactionRequestDto,
    ): Response<TransactionResponseDto>

    @PUT("accounts/{id}")
    suspend fun updateAccount(
        @Header("Authorization") token: String = "Bearer 6JpN0O3FBKiO0MxjHtUVb3ST",
        @Path("id") id: Int,
        @Body account: AccountRequestDto,
    ): Response<AccountDto>

    @DELETE("transactions/{id}")
    suspend fun deleteTransaction(
        @Header("Authorization") token: String = "Bearer 6JpN0O3FBKiO0MxjHtUVb3ST",
        @Path("id") id: Int,
    ): Response<Unit>
}
