package com.example.unscramblegame.game.data

import com.example.unscramblegame.core.data.BooleanCache
import com.example.unscramblegame.core.data.IntCache
import com.example.unscramblegame.core.data.StringCache
import com.example.unscramblegame.game.presentation.GameScreen
import com.example.unscramblegame.load.data.cache.CacheDataSource

interface GameRepository {

    suspend fun updateCurrentWord()
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
        private val max: Int,
        private val score: IntCache,
        private val uiIndex: IntCache,
        private val currentIndex: IntCache,
        private val failed: BooleanCache,
        private val corrects: IntCache,
        private val incorrects: IntCache,
        private val skips: IntCache,
        private val cachedWords: CacheDataSource,
        private val lastScreen: StringCache
    ) : GameRepository {

        private lateinit var cachedWord: String

        override suspend fun updateCurrentWord() {
            cachedWord = cachedWords.read(currentIndex.read())
        }

        override fun currentWord() = cachedWord

        override fun currentScore(): String {
            return score.read().toString()
        }

        override fun currentCounter(): String {
            return "${uiIndex.read()}/$max"
        }

        override fun sameLength(userInput: String): Boolean {
            return userInput.length == cachedWord.length
        }

        override fun check(userInput: String): Boolean {
            return if (cachedWord.equals(userInput, ignoreCase = true)) {
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

            if (currentIndex.read() == max)
                currentIndex.save(0)
            uiIndex.save(uiIndex.read() + 1)
            failed.save(false)
        }

        override fun reset() {
            currentIndex.save(currentIndex.read() + 1)
            if (currentIndex.read() == max)
                currentIndex.save(0)

            uiIndex.save(1)
            failed.save(false)
        }

        override fun incrementSkips() {
            skips.save(skips.read() + 1)
        }

        override fun saveLastScreenIsGame() {
            GameScreen::class.java.canonicalName?.let { lastScreen.save(it) }
        }
    }
}
