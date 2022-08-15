package com.example.currencyconvertertechtix.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.currencyconvertertechtix.Currency

@Dao
interface CurrenciesDAO {
    @Insert(entity = Currency::class)
    suspend fun addCurrencies(c: Currency)

    @Query("SELECT * FROM Currency")
    fun getCurrency(): LiveData<List<Currency>>

    @Query("DELETE FROM Currency")
    suspend fun deleteCurrency()
}