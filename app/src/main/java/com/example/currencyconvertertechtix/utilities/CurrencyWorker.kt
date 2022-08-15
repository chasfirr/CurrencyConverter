package com.example.currencyconvertertechtix.utilities

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.currencyconvertertechtix.Currency
import com.example.currencyconvertertechtix.apis.CurrencyApi
import com.example.currencyconvertertechtix.apis.RetrofitHelper
import com.example.currencyconvertertechtix.database.CurrencyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class CurrencyWorker(private val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {

        val api = RetrofitHelper.getInstance().create(CurrencyApi::class.java)
        val dao = CurrencyDatabase.getDB(context).currencyDAO()
        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getCurrencies()
            if (response.isSuccessful && response.body() != null) {
                dao.deleteCurrency()
                for (k in response.body()!!.symbols.keys) {
                    dao.addCurrencies(Currency(k, response.body()!!.symbols[k]!!))
                }
            } else {
                Log.d("CURRENCY_LIST_ERROR", response.errorBody()!!.string())
            }
        }

        return Result.success()
    }
}