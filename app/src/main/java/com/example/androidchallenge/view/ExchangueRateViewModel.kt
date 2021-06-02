package com.example.androidchallenge.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidchallenge.domain.model.CurrencyTypeModel
import com.example.androidchallenge.domain.usecase.GetCurrencyTypeUseCase
import com.example.androidchallenge.domain.usecase.GetCurrencyUseCase
import com.example.androidchallenge.util.LocalSharedPreference
import com.example.androidchallenge.view.currency.CurrencyState
import com.example.androidchallenge.view.currency.Error
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExchangueRateViewModel(
        private val getCurrencyTypeUseCase: GetCurrencyTypeUseCase,
        private val getCurrencyUseCase: GetCurrencyUseCase
): ViewModel() {

    private var _currency = MutableLiveData<CurrencyState>()
    private var _currencyType = MutableLiveData<List<CurrencyTypeModel>>()

    val currency: LiveData<CurrencyState>
        get() {
            return _currency
        }

    val currencyType: LiveData<List<CurrencyTypeModel>>
        get() {
            return _currencyType
        }

    fun getCurrencyTypeLocal(sharedPreference: LocalSharedPreference, jsonFile: String) {
        val type = object : TypeToken<List<CurrencyTypeModel>>() {}.type
        val currencyType = Gson().fromJson<List<CurrencyTypeModel>>(
                sharedPreference.getValue(LocalSharedPreference.PREFERENCE_CURRENCY_TYPE), type
        )
        if(currencyType != null) {
            _currencyType.value = currencyType
        } else {
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    getCurrencyTypeUseCase(jsonFile)
                }
                sharedPreference.saveKey(LocalSharedPreference.PREFERENCE_CURRENCY_TYPE, Gson().toJson(result))
                _currencyType.value = result
            }
        }
    }

    fun getCurrencyLocal(sharedPreference: LocalSharedPreference, jsonFile: String, code: String) {
        val type = object : TypeToken<CurrencyTypeModel>() {}.type
        val currency = Gson().fromJson<CurrencyTypeModel>(
            sharedPreference.getValue(LocalSharedPreference.PREFERENCE_CURRENCY), type
        )
        if(currency != null) {
            _currency.value = CurrencyState.GetCurrencyType(currency)
        } else {
            viewModelScope.launch {
                val result = withContext(Dispatchers.IO) {
                    getCurrencyUseCase(jsonFile, code)
                }
                if (result != null) {
                    _currency.value = CurrencyState.GetCurrencyType(result)
                } else {
                    _currency.value = CurrencyState.GetCurrencyTypeError(Error.GENERAL)
                }
            }
        }
    }
}