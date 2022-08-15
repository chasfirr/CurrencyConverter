package com.example.currencyconvertertechtix.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconvertertechtix.ConvertedCurrency
import com.example.currencyconvertertechtix.Currency
import com.example.currencyconvertertechtix.CurrencyJson
import com.example.currencyconvertertechtix.repository.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class CurrencyViewModel(private val repository: CurrencyRepository) :
    ViewModel() {

    private val currencies = ArrayList<String>()
    private val convertedCurrenciesLive = MutableLiveData<List<ConvertedCurrency>>()
    private var convertedCurrencies = ArrayList<ConvertedCurrency>()
    private var job: Job? = null

    fun setCurrencies(c: ArrayList<String>) {
        currencies.clear()
        currencies.addAll(c)
    }

    private fun addConvertedCurrency(c: ConvertedCurrency) {
        convertedCurrencies.add(c)
        convertedCurrenciesLive.value = convertedCurrencies
    }

    fun getConvertedCurrencies(): LiveData<List<ConvertedCurrency>> {
        return convertedCurrenciesLive
    }

    fun convertCurrency(from: String, amt: String) {
        if (job != null && job!!.isActive) {
            job!!.cancel()
        }
        resetConvertedCurrencies()
        viewModelScope.launch(Dispatchers.IO) {
            job = launch {
                if (currencies.isNotEmpty()) {
                    for (k in 0 until 2) {
                        val response = repository.convertCurrency(currencies[k], from, amt)
                        if (response.isSuccessful && response.body() != null) {
                            val body = response.body()
                            withContext(Dispatchers.Main) {
                                addConvertedCurrency(
                                    ConvertedCurrency(
                                        body!!.query.to,
                                        body.result
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun resetConvertedCurrencies() {
        convertedCurrencies = ArrayList()
        convertedCurrenciesLive.value = convertedCurrencies
    }


    fun getCurrencies(): LiveData<List<Currency>> {
        return repository.getCurrencyFromDB()
    }

    suspend fun fetchCurrenciesFromServer(): Response<CurrencyJson> {
        return repository.getCurrenciesFromServer()
    }

    fun addCurrencies(c: Currency) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addCurrencies(c)
        }
    }

}