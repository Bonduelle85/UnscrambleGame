package com.example.unscramblegame.presentation.gameover

import android.content.Context
import com.example.unscramblegame.R
import com.example.unscramblegame.data.GameOverRepository

class GameOverViewModel(
    private val statistics: Statistics,
    private val repository: GameOverRepository,
) {

    fun stats(): String {
        val makeStatistics = statistics.makeStatistics(
            repository.getCurrentScore(),
            repository.getCorrects(),
            repository.getIncorrecs(),
            repository.getSkips()
        )
        repository.clear()
        return makeStatistics
    }
}

interface Statistics {
    fun makeStatistics(score: String, corrects: String, incorrects: String, skips: String): String

    class Base(private val context: Context) : Statistics {
        override fun makeStatistics(
            score: String,
            corrects: String,
            incorrects: String,
            skips: String
        ): String {
            return context.getString(R.string.scored, score, corrects, incorrects, skips)
        }
    }
}
