package com.example.androidchallenge.data.repository

import com.example.androidchallenge.data.service.CurrencyDataStoreImpl
import com.example.androidchallenge.domain.repository.CurrencyRepository
import com.example.androidchallenge.domain.model.CurrencyTypeModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrencyRepositoryImpl: CurrencyRepository {
    override suspend fun getCurrencyType(jsonFile: String): List<CurrencyTypeModel> {
        return CurrencyDataStoreImpl().getCurrencyType(jsonFile)
    }

    override suspend fun getCurrency(jsonFile: String, code: String): CurrencyTypeModel? {
        return CurrencyDataStoreImpl().getCurrency(jsonFile, code)
    }
}