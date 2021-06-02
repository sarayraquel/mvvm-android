package com.example.androidchallenge.data.repository

import com.example.androidchallenge.domain.repository.CurrencyTypeRepository
import com.example.androidchallenge.domain.model.CurrencyTypeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrencyTypeTypeRepositoryImpl: CurrencyTypeRepository {
    override suspend fun getCurrencyType(jsonFile: String): List<CurrencyTypeModel> {
        val gson = Gson()
        val currencyType = object : TypeToken<List<CurrencyTypeModel>>() {}.type
        return gson.fromJson(jsonFile, currencyType)
    }

    override suspend fun getCurrency(jsonFile: String, code: String): CurrencyTypeModel? {
        val currencyType = getCurrencyType(jsonFile)
        return currencyType.firstOrNull() {
            it.code == code
        }
    }
}