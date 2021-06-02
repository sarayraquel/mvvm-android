package com.example.androidchallenge.view

enum class CurrencyOption(val id: String, val currency: String, val description: String) {
    PEN("PEN", "Soles", "Perú"),
    USD("USD", "Dólares", "European Union"),
    EUR("EUR", "Euros", "United States"),
    JPY("JPY", "Yen", "Japan"),
    GBP("GBP", "Libra Esterlina", "United Kingdom"),
    CHF("CHF", "Franco Suizo", "Switzerland"),
    CAD("CAD", "Dólar Canadiense", "Canada"),
    NONE("", "", "");

    companion object {
        fun findById(id: String) : CurrencyOption = values().find { it.id == id } ?: NONE
        fun findByDescription(description: String) : CurrencyOption = values().find { it.currency == description } ?: NONE
    }
}