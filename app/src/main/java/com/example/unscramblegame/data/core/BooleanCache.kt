package com.example.unscramblegame.data.core

import android.content.SharedPreferences

interface BooleanCache {

    fun save(value: Boolean)

    fun read(): Boolean

    class Base(
        private val key: String,
        private val sharedPreferences: SharedPreferences,

        ) : BooleanCache {

        override fun save(value: Boolean) {
            sharedPreferences.edit()
                .putBoolean(key, value)
                .apply()
        }

        override fun read(): Boolean {
            return sharedPreferences.getBoolean(key, false)
        }
    }
}