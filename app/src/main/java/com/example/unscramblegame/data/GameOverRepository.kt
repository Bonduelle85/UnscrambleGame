package com.example.unscramblegame.data

import com.example.unscramblegame.data.core.IntCache

interface GameOverRepository {

    fun getCurrentScore(): String
    fun clearCurrentScore()
    fun getCorrects(): String
    fun clearCorrects()
    fun getIncorrecs(): String
    fun clearIncorrects()
    fun getSkips(): String
    fun clearSkips()

    class Base(
        private val score: IntCache,
        private val corrects: IntCache,
        private val incorrects: IntCache,
        private val skips: IntCache,
    ) : GameOverRepository {

        override fun getCurrentScore(): String {
            return score.read().toString()
        }

        override fun clearCurrentScore() {
            score.save(0)
        }

        override fun getCorrects(): String {
            return corrects.read().toString()
        }

        override fun clearCorrects() {
            corrects.save(0)
        }

        override fun getIncorrecs(): String {
            return incorrects.read().toString()
        }

        override fun clearIncorrects() {
            incorrects.save(0)
        }

        override fun getSkips(): String {
            return skips.read().toString()
        }

        override fun clearSkips() {
            skips.save(0)
        }
    }
}
