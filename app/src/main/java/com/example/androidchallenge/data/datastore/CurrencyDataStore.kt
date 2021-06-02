package com.example.androidchallenge.data.datastore

import com.example.androidchallenge.domain.model.CurrencyTypeModel

interface CurrencyDataStore {
    suspend fun getCurrencyType(jsonFile: String): List<CurrencyTypeModel>
    suspend fun getCurrency(jsonFile: String, code: String): CurrencyTypeModel?
}