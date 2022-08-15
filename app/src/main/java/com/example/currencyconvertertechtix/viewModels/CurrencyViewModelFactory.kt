package com.example.currencyconvertertechtix.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.currencyconvertertechtix.repository.CurrencyRepository

class CurrencyViewModelFactory(private val rep: CurrencyRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return CurrencyViewModel(rep) as T
    }

}
