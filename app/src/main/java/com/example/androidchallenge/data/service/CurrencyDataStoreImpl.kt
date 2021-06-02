package com.example.androidchallenge.data.service

import com.example.androidchallenge.data.datastore.CurrencyDataStore
import com.example.androidchallenge.data.mapper.toDomain
import com.example.androidchallenge.data.server.CurrencyTypeServer
import com.example.androidchallenge.domain.model.CurrencyTypeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrencyDataStoreImpl: CurrencyDataStore {
    override suspend fun getCurrencyType(jsonFile: String): List<CurrencyTypeModel> {
        val gson = Gson()
        val currency = object : TypeToken<List<CurrencyTypeServer>>() {}.type
        val server = (gson.fromJson(
                jsonFile,
                currency
        ) as List<CurrencyTypeServer>).toList()
        return server.map { it.toDomain() }
    }

    override suspend fun getCurrency(jsonFile: String, code: String): CurrencyTypeModel? {
        val currencyType = getCurrencyType(jsonFile)
        return currencyType.firstOrNull() {
            it.code == code
        }
    }
}