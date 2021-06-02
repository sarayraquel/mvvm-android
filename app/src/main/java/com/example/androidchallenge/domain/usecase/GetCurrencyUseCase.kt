package com.example.androidchallenge.domain.usecase

import com.example.androidchallenge.domain.model.CurrencyTypeModel
import com.example.androidchallenge.domain.repository.CurrencyTypeRepository

class GetCurrencyUseCase(private val currencyTypeRepository: CurrencyTypeRepository) {
    suspend operator fun invoke(jsonFile: String, code: String): CurrencyTypeModel? {
        return currencyTypeRepository.getCurrency(jsonFile, code)
    }
}