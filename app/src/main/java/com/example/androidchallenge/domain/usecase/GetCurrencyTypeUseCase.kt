package com.example.androidchallenge.domain.usecase

import com.example.androidchallenge.domain.repository.CurrencyTypeRepository
import com.example.androidchallenge.domain.model.CurrencyTypeModel

class GetCurrencyTypeUseCase(private val currencyTypeRepository: CurrencyTypeRepository) {
    suspend operator fun invoke(jsonFile: String)
            : List<CurrencyTypeModel> {
        return currencyTypeRepository.getCurrencyType(jsonFile)
    }
}