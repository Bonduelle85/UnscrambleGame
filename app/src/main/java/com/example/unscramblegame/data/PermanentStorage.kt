package com.example.unscramblegame.data

import android.content.Context
import android.content.SharedPreferences
import com.example.unscramblegame.R

interface PermanentStorage {
    fun saveCurrentIndex(currentIndex: Int)
    fun currentIndex(): Int

    fun saveUiIndex(uiIndex: Int)
    fun uiIndex(): Int

    fun saveScore(score: Int)
    fun score(): Int

    fun saveFailed(failed: Boolean)
    fun failed(): Boolean

    class Base(
        context: Context
    ) : PermanentStorage {

        private val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

        override fun saveCurrentIndex(currentIndex: Int) {
            sharedPreferences.edit().putInt(CURRENT_INDEX_KEY, currentIndex).apply()
        }

        override fun currentIndex(): Int {
            return sharedPreferences.getInt(CURRENT_INDEX_KEY, 0)
        }

        override fun saveUiIndex(uiIndex: Int) {
            sharedPreferences.edit().putInt(UI_INDEX_KEY, uiIndex).apply()
        }

        override fun uiIndex(): Int {
            return sharedPreferences.getInt(UI_INDEX_KEY, 1)
        }

        override fun saveScore(score: Int) {
            sharedPreferences.edit().putInt(SCORE_KEY, score).apply()
        }

        override fun score(): Int {
            return sharedPreferences.getInt(SCORE_KEY, 0)
        }

        override fun saveFailed(failed: Boolean) {
            sharedPreferences.edit().putBoolean(FAILED_KEY, failed).apply()
        }

        override fun failed(): Boolean {
            return sharedPreferences.getBoolean(FAILED_KEY, false)
        }

        companion object {
            private const val CURRENT_INDEX_KEY = "CURRENT_INDEX_KEY"
            private const val UI_INDEX_KEY = "UI_INDEX_KEY"
            private const val SCORE_KEY = "SCORE_KEY"
            private const val FAILED_KEY = "FAILED_KEY"
        }
    }
}
