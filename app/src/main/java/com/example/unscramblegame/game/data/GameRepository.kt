package com.example.unscramblegame.game.data

import com.example.unscramblegame.core.data.BooleanCache
import com.example.unscramblegame.core.data.DataSource
import com.example.unscramblegame.core.data.IntCache
import com.example.unscramblegame.core.data.StringCache
import com.example.unscramblegame.game.presentation.GameScreen

interface GameRepository {

    fun currentWord(): String
    fun currentScore(): String
    fun currentCounter(): String
    fun sameLength(userInput: String): Boolean
    fun check(userInput: String): Boolean
    fun isLast(): Boolean
    fun next()
    fun reset()
    fun incrementSkips()
    fun saveLastScreenIsGame()

    class Base(
        private val score: IntCache,
        private val uiIndex: IntCache,
        private val currentIndex: IntCache,
        private val failed: BooleanCache,
        private val corrects: IntCache,
        private val incorrects: IntCache,
        private val skips: IntCache,
        dataSource: DataSource,
        private val max: Int,
        private val lastScreen: StringCache
    ) : GameRepository {

        private val list = dataSource.data()

        override fun currentWord(): String {
            return list[currentIndex.read()]
        }

        override fun currentScore(): String {
            return score.read().toString()
        }

        override fun currentCounter(): String {
            return "${uiIndex.read()}/$max"
        }

        override fun sameLength(userInput: String): Boolean {
            return userInput.length == currentWord().length
        }

        override fun check(userInput: String): Boolean {
            return if (currentWord().equals(userInput, ignoreCase = true)) {
                val oldScore = score.read()
                val newScore = oldScore + if (failed.read()) 10 else 20
                score.save(newScore)
                corrects.save(corrects.read() + 1)
                true
            } else {
                failed.save(true)
                incorrects.save(incorrects.read() + 1)
                false
            }
        }

        override fun isLast(): Boolean {
            return uiIndex.read() == max
        }

        override fun next() {
            currentIndex.save(currentIndex.read() + 1)

            if (currentIndex.read() == list.size)
                currentIndex.save(0)
            uiIndex.save(uiIndex.read() + 1)
            failed.save(false)
        }

        override fun reset() {
            currentIndex.save(currentIndex.read() + 1)
            if (currentIndex.read() == list.size)
                currentIndex.save(0)

            uiIndex.save(1)
            failed.save(false)
        }

        override fun incrementSkips() {
            skips.save(skips.read() + 1)
        }

        override fun saveLastScreenIsGame() {
            lastScreen.save(GameScreen::class.java.canonicalName)
        }
    }
}
