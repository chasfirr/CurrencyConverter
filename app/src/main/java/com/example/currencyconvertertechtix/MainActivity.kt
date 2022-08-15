package com.example.currencyconvertertechtix

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.currencyconvertertechtix.adapters.RecyclerAdapter
import com.example.currencyconvertertechtix.apis.CurrencyApi
import com.example.currencyconvertertechtix.apis.RetrofitHelper
import com.example.currencyconvertertechtix.database.CurrencyDatabase
import com.example.currencyconvertertechtix.databinding.ActivityMainBinding
import com.example.currencyconvertertechtix.repository.CurrencyRepository
import com.example.currencyconvertertechtix.utilities.CurrencyWorker
import com.example.currencyconvertertechtix.validators.validator
import com.example.currencyconvertertechtix.viewModels.CurrencyViewModel
import com.example.currencyconvertertechtix.viewModels.CurrencyViewModelFactory
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: CurrencyViewModel
    private var from: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val dao = CurrencyDatabase.getDB(this).currencyDAO()
        val api = RetrofitHelper.getInstance().create(CurrencyApi::class.java)
        val repository = CurrencyRepository(api, dao)
        viewModel = ViewModelProvider(
            this,
            CurrencyViewModelFactory(repository)
        )[CurrencyViewModel::class.java]

        viewModel.getCurrencies().observe(this) {
            if (it.isNotEmpty()) {
                stopLoading()
                val symbols: ArrayList<String> = it.map { c -> c.Symbol } as ArrayList<String>
                viewModel.setCurrencies(symbols)
                from = symbols[0]
                setUpSpinner(symbols)
            }
        }
        /*
        if (it != null && it.isNotEmpty()) {
            setUpSpinner(it.map { c -> c.Symbol })
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                val response = viewModel.fetchCurrenciesFromServer()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body() != null) {
                        val sym = response.body()!!.symbols
                        if (sym.isNotEmpty()) {
                            setUpSpinner(sym.keys.toList())
                        }
                    } else {
                        Utility.showAlert(
                            this@MainActivity,
                            "Network or CurrencyLayer error!"
                        )
                    }
                }
            }
        }
        */
        binding.edittext.doOnTextChanged { text, start, before, count ->
            if (text != null && from != null) {
                if (validator.validateAmount(text.toString())
                    && validator.validateCurrencySymbol(from!!)
                ) {
                    startLoading()
                    viewModel.convertCurrency(from!!, text.toString())
                }
            }
        }


        viewModel.getConvertedCurrencies().observe(this)
        {
            if (it.isNotEmpty()) {
                stopLoading()
                binding.recyclerView.adapter = RecyclerAdapter(it)
            }
        }

        setUpWorker()

    }

    private fun setUpSpinner(symbols: List<String>) {
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, symbols)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, pos: Int, p3: Long) {
                from = symbols[pos]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //
            }

        }
    }

    private fun startLoading() {
        if (binding.progressBar.visibility == View.GONE) {
            binding.progressBar.visibility = View.VISIBLE
        }
    }

    private fun stopLoading() {
        if (binding.progressBar.visibility == View.VISIBLE) {
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun setUpWorker() {
        val constraint =
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workerRequest = PeriodicWorkRequest
            .Builder(CurrencyWorker::class.java, 30L, TimeUnit.MINUTES)
            .setConstraints(constraint)
            .build()
        WorkManager.getInstance(applicationContext).enqueue(workerRequest)
    }
}