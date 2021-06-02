package com.example.androidchallenge.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import com.example.androidchallenge.R
import com.example.androidchallenge.data.repository.CurrencyTypeTypeRepositoryImpl
import com.example.androidchallenge.domain.model.CurrencyTypeModel
import com.example.androidchallenge.domain.usecase.GetCurrencyTypeUseCase
import com.example.androidchallenge.domain.usecase.GetCurrencyUseCase
import com.example.androidchallenge.util.*
import com.example.androidchallenge.view.currency.CurrencyState
import kotlinx.android.synthetic.main.activity_exchangue_rate.*

class ExchangueRateActivity : AppCompatActivity() {

    private lateinit var exchangueRateViewModel: ExchangueRateViewModel
    private var jsonFileString: String? = null
    private var currentTypeList: List<CurrencyTypeModel>? = null
    private var currencyCountryOption: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exchangue_rate)
        tvOrigin.text = Constants.amountOrigin

        exchangueRateViewModel = getViewModel {
            ExchangueRateViewModel(
                    GetCurrencyTypeUseCase(CurrencyTypeTypeRepositoryImpl()),
                    GetCurrencyUseCase(CurrencyTypeTypeRepositoryImpl())
            )
        }

        exchangueRateViewModel.currencyType.observe(this, Observer(::showCurrencyType))
        exchangueRateViewModel.currency.observe(this, Observer(::showCurrency))

        fbRefresh.setOnClickListener {
            updateValuesCurrency()
            currencyCountryOption = null
            jsonFileString?.let {
                exchangueRateViewModel.getCurrencyLocal(
                        LocalSharedPreference(this),
                    it,
                    CurrencyOption.findByDescription(buttonOrigin.text.toString()).id
                )
            }
        }

        buttonOrigin.setOnLongClickListener {
            currencyCountryOption = Constants.currencyCountryOrigin
            startActivity(Intent(this, CurrencyTypeActivity::class.java))
            true
        }

        buttonDestination.setOnLongClickListener {
            currencyCountryOption = Constants.currencyCountryDestination
            startActivity(Intent(this, CurrencyTypeActivity::class.java))
            true
        }
    }

    override fun onResume() {
        super.onResume()
        jsonFileString = getJsonDataFromAsset(applicationContext, Constants.jsonCurrency)
        jsonFileString?.let {
            exchangueRateViewModel.getCurrencyTypeLocal(
                    LocalSharedPreference(this),
                    it
            )
        }

        if(jsonFileString == null) {
            currencyViewDefault()
        }
    }

    private fun showCurrencyType(currencyTypeModel: List<CurrencyTypeModel>) {
        this.currentTypeList = currencyTypeModel
        jsonFileString?.let {
            exchangueRateViewModel.getCurrencyLocal(
                    LocalSharedPreference(this),
                    it,
                    CurrencyOption.USD.currency
            )
        }
    }

    private fun showCurrency(currencyState: CurrencyState) {
        when(currencyState) {
            is CurrencyState.GetCurrencyType -> {
                currencyViewLocal(currencyState.currencyTypeModel)
            }
            is CurrencyState.GetCurrencyTypeError -> {
                currencyViewDefault()
            }
        }
    }

    private fun currencyViewLocal(currencyTypeModel: CurrencyTypeModel) {
        tvBuy.text = currencyTypeModel.rate.toString()
        tvSale.text = currencyTypeModel.rate.toString()
        selectCountryFromExhangueRate(currencyTypeModel)
        val currencyOrigin = CurrencyOption.findByDescription(buttonOrigin.text.toString()).id
        val currencyDestination = CurrencyOption.findByDescription(buttonDestination.text.toString()).id
        conversionProcess(currencyOrigin, currencyDestination,tvOrigin.text.toString())
    }

    private fun selectCountryFromExhangueRate(currencyTypeModel: CurrencyTypeModel) {
        if(currencyCountryOption != null) {
            if (currencyCountryOption == Constants.currencyCountryOrigin) {
                buttonOrigin.text = currencyTypeModel.code?.let { CurrencyOption.findById(it).currency }
            } else {
                buttonDestination.text = currencyTypeModel.code?.let { CurrencyOption.findById(it).currency }
            }
        }
    }

    private fun currencyViewDefault() {
        val currencyType = CurrencyTypeModel()
        tvBuy.text = currencyType.rate.toString()
        tvSale.text = currencyType.rate.toString()
        buttonOrigin.text = CurrencyOption.USD.currency
        buttonDestination.text = CurrencyOption.PEN.currency
        val currencyOrigin = CurrencyOption.findByDescription(buttonOrigin.text.toString()).id
        val currencyDestination = CurrencyOption.findByDescription(buttonDestination.text.toString()).id
        conversionProcess(currencyOrigin, currencyDestination,tvOrigin.text.toString())
    }

    private fun updateValuesCurrency() {
        val origin = buttonOrigin.text
        val destination = buttonDestination.text
        buttonOrigin.text = destination
        buttonDestination.text = origin
    }

    private fun conversionProcess(
        currencyOrigin: String, currencyDestination: String,
        exchangueAmount: String) {
        val currencyOrig = currentTypeList?.first { it.code == currencyOrigin }
        val currencyDest = currentTypeList?.first { it.code == currencyDestination }

        if(currencyOrig != null && currencyDest != null){
            val conversion = calculate( exchangueAmount.toDouble(),currencyOrig.rate, currencyDest.rate)
            tvDestination.text = conversion.toString()
        }
    }
}