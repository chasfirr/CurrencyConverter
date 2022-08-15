package com.example.currencyconvertertechtix.repository

import androidx.lifecycle.LiveData
import com.example.currencyconvertertechtix.ConvertJson
import com.example.currencyconvertertechtix.Currency
import com.example.currencyconvertertechtix.CurrencyJson
import com.example.currencyconvertertechtix.apis.CurrencyApi
import com.example.currencyconvertertechtix.database.CurrenciesDAO
import retrofit2.Response

class CurrencyRepository(private val service: CurrencyApi, private val dao: CurrenciesDAO) {

    fun getCurrencyFromDB(): LiveData<List<Currency>> {
        return dao.getCurrency()
    }

    suspend fun getCurrenciesFromServer(): Response<CurrencyJson> {
        return service.getCurrencies()
    }


    suspend fun addCurrencies(c: Currency) {
        dao.addCurrencies(c)
    }

    suspend fun convertCurrency(to: String, from: String, amt: String): Response<ConvertJson> {
        return service.convertCurrency(to, from, amt)
    }
}