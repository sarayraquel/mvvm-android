package com.example.androidchallenge.domain.repository

import com.example.androidchallenge.domain.model.CurrencyTypeModel

interface CurrencyRepository {
    suspend fun getCurrencyType(jsonFile: String): List<CurrencyTypeModel>
    suspend fun getCurrency(jsonFile: String, code: String): CurrencyTypeModel?
}