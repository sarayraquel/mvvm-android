package com.example.androidchallenge.view.currency

import com.example.androidchallenge.domain.model.CurrencyTypeModel

sealed class CurrencyState() {
    data class GetCurrencyType(val currencyTypeModel: CurrencyTypeModel): CurrencyState()
    class GetCurrencyTypeError(error: Error): CurrencyState()
}

enum class Error {
    GENERAL
}