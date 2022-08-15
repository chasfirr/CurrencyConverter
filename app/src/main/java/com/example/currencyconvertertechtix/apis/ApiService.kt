package com.example.currencyconvertertechtix.apis

import com.example.currencyconvertertechtix.ConvertJson
import com.example.currencyconvertertechtix.CurrencyJson
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CurrencyApi {


    @Headers("apikey:${RetrofitHelper.API_KEY}")
    @GET("list")
    suspend fun getCurrencies(): retrofit2.Response<CurrencyJson>

    @Headers("apikey:${RetrofitHelper.API_KEY}")
    @GET("convert?")
    suspend fun convertCurrency(
        @Query("to") to: String,
        @Query("from") from: String,
        @Query("amount") amount: String
    ): retrofit2.Response<ConvertJson>

}