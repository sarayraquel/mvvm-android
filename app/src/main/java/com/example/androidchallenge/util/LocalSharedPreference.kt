package com.example.androidchallenge.util

import android.content.Context
import android.content.SharedPreferences

class LocalSharedPreference(context: Context) {

    private val sharedPreferences: SharedPreferences
    companion object {
        private const val PREFERENCES_NAME = "localSharedPreference"
        const val PREFERENCE_CURRENCY_TYPE = "currencyType"
        const val PREFERENCE_CURRENCY = "currency"
    }

    init {
        sharedPreferences =
            context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    fun saveKey(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getValue(key: String): String? {
        return sharedPreferences.getString(key, null)
    }
}