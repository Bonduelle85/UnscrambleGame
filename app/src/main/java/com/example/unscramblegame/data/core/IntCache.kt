package com.example.unscramblegame.data.core

import android.content.SharedPreferences

interface IntCache {

    fun save(value: Int)

    fun read(): Int

    class Base(
        private val key: String,
        private val sharedPreferences: SharedPreferences,
        private val defValue: Int = 0,
    ) : IntCache {

        override fun save(value: Int) {
            sharedPreferences.edit().putInt(key, value).apply()
        }

        override fun read(): Int {
            return sharedPreferences.getInt(key, defValue)
        }
    }
}