package com.example.androidchallenge.domain.usecase

import com.example.androidchallenge.domain.repository.CurrencyRepository
import com.example.androidchallenge.domain.model.CurrencyTypeModel

class GetCurrencyTypeUseCase(private val currencyRepository: CurrencyRepository) {
    suspend operator fun invoke(jsonFile: String)
            : List<CurrencyTypeModel> {
        return currencyRepository.getCurrencyType(jsonFile)
    }
}