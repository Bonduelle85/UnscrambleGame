package com.example.unscramblegame.data

import android.content.SharedPreferences

interface IntCache {

    fun save(value: Int)

    fun read(): Int

    class Base(
        private val key: String,
        private val sharedPreferences: SharedPreferences,
    ) : IntCache {

        override fun save(value: Int) {
            sharedPreferences.edit().putInt(key, value).apply()
        }

        override fun read(): Int {
            return sharedPreferences.getInt(key, 0)
        }
    }
}