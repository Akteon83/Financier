package com.atech.financier.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/** Синглтон для взаимодействия с сервером при помощи Retrofit */
object RetrofitInstance {

    val api: FinancierApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://shmr-finance.ru/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FinancierApi::class.java)
    }
}
