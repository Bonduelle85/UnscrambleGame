package com.example.unscramblegame.data

import com.example.unscramblegame.data.core.IntCache
import com.example.unscramblegame.data.core.StringCache
import com.example.unscramblegame.presentation.gameover.GameOverScreen

interface GameOverRepository {

    fun getCurrentScore(): String

    fun getCorrects(): String

    fun getIncorrecs(): String

    fun getSkips(): String

    fun clear()

    fun saveLastScreenIsGameOver()

    class Base(
        private val score: IntCache,
        private val corrects: IntCache,
        private val incorrects: IntCache,
        private val skips: IntCache,
        private val lastScreen: StringCache,
    ) : GameOverRepository {

        override fun getCurrentScore(): String {
            return score.read().toString()
        }

        override fun getCorrects(): String {
            return corrects.read().toString()
        }

        override fun getIncorrecs(): String {
            return incorrects.read().toString()
        }

        override fun getSkips(): String {
            return skips.read().toString()
        }

        override fun clear() {
            skips.save(0)
            incorrects.save(0)
            corrects.save(0)
            score.save(0)
        }

        override fun saveLastScreenIsGameOver() {
            lastScreen.save(GameOverScreen::class.java.canonicalName)
        }
    }
}
