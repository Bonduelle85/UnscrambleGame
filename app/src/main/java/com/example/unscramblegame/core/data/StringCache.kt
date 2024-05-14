package com.example.unscramblegame.core.data

import android.content.SharedPreferences

interface StringCache {

    fun save(value: String)

    fun read(): String

    class Base(
        private val key: String,
        private val sharedPreferences: SharedPreferences,
        private val defValue: String,
    ) : StringCache {

        override fun save(value: String) {
            sharedPreferences.edit()
                .putString(key, value)
                .apply()
        }

        override fun read(): String {
            return sharedPreferences.getString(key, defValue) ?: "default value"
        }
    }
}
