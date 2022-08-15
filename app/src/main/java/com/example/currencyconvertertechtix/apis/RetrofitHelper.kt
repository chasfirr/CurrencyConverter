package com.example.currencyconvertertechtix.apis

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    const val API_KEY = "r1bTAQLbvYW1BJa7Uk7bVMy04Koylbw5"
    private const val BASE_URL = "https://api.apilayer.com/currency_data/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
}