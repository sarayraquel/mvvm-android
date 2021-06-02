package com.example.androidchallenge.domain.usecase

import com.example.androidchallenge.domain.model.CurrencyTypeModel
import com.example.androidchallenge.domain.repository.CurrencyRepository

class GetCurrencyUseCase(private val currencyRepository: CurrencyRepository) {
    suspend operator fun invoke(jsonFile: String, code: String): CurrencyTypeModel? {
        return currencyRepository.getCurrency(jsonFile, code)
    }
}