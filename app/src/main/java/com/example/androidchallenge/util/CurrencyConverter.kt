package com.example.androidchallenge.util

@Throws(Exception::class)
fun calculate(
    exchangueAmount: Double,
    currencyOrigin: Double,
    currencyDestination: Double
): Double? {
    if (currencyOrigin != 0.0 && currencyDestination != 0.0){
        return currencyDestination / currencyOrigin * exchangueAmount
    } else
        throw java.lang.Exception(
        "Currency not found."
    )
}