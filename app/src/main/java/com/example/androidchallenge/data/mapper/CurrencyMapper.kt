package com.example.androidchallenge.data.mapper

import com.example.androidchallenge.data.server.CurrencyTypeServer
import com.example.androidchallenge.domain.model.CurrencyTypeModel

fun CurrencyTypeServer.toDomain() = CurrencyTypeModel(
        code = code,
        description = description,
        rate = rate
)