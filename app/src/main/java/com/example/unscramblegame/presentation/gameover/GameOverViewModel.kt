package com.example.unscramblegame.presentation.gameover

import com.example.unscramblegame.data.GameOverRepository

class GameOverViewModel(
    private val repository: GameOverRepository
) {

    fun getScore(): String {
        val score = repository.getCurrentScore()
        repository.clearCurrentScore()
        return score
    }

    fun getCorrects(): String {
        val corrects = repository.getIncorrecs()
        repository.clearCorrects()
        return corrects
    }

    fun getIncorrects(): String {
        val incorrects = repository.getIncorrecs()
        repository.clearIncorrects()
        return incorrects
    }

    fun getSkips(): String {
        val skips = repository.getSkips()
        repository.clearSkips()
        return skips
    }
}
