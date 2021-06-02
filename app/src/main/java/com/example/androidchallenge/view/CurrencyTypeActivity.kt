package com.example.androidchallenge.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.androidchallenge.R
import com.example.androidchallenge.data.repository.CurrencyTypeTypeRepositoryImpl
import com.example.androidchallenge.domain.model.CurrencyTypeModel
import com.example.androidchallenge.domain.usecase.GetCurrencyTypeUseCase
import com.example.androidchallenge.domain.usecase.GetCurrencyUseCase
import com.example.androidchallenge.util.Constants
import com.example.androidchallenge.util.LocalSharedPreference
import com.example.androidchallenge.util.getJsonDataFromAsset
import com.example.androidchallenge.util.getViewModel
import com.example.androidchallenge.view.adaptador.CurrencyTypeAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_currency_type.*

class CurrencyTypeActivity : AppCompatActivity() {

    private lateinit var exchangueRateViewModel: ExchangueRateViewModel
    private var jsonFileString: String? = null
    lateinit var currencyTypeAdapter: CurrencyTypeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_currency_type)

        exchangueRateViewModel = getViewModel {
            ExchangueRateViewModel(
                    GetCurrencyTypeUseCase(CurrencyTypeTypeRepositoryImpl()),
                    GetCurrencyUseCase(CurrencyTypeTypeRepositoryImpl())
            )
        }

        exchangueRateViewModel.currencyType.observe(this, Observer(::setCurrencyTypeAdapter))
        jsonFileString = getJsonDataFromAsset(applicationContext, Constants.jsonCurrency)
        jsonFileString?.let {
            exchangueRateViewModel.getCurrencyTypeLocal(
                LocalSharedPreference(this),
                it
            )
        }
    }

    private fun setCurrencyTypeAdapter(currencyTypeModel: List<CurrencyTypeModel>) {
        currencyTypeAdapter = CurrencyTypeAdapter(currencyTypeModel)
        currencyTypeRecyclerView.adapter = currencyTypeAdapter.apply {
            onclickListener = { goBackExchangueRate(it) }
        }
    }

    private fun goBackExchangueRate(model: CurrencyTypeModel) {
        val sharedPreference = LocalSharedPreference(this)
        sharedPreference.saveKey(LocalSharedPreference.PREFERENCE_CURRENCY, Gson().toJson(model))
        finish()
    }
}