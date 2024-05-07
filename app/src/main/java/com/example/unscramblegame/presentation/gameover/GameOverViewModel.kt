package com.example.unscramblegame.presentation.gameover

import com.example.unscramblegame.data.GameOverRepository

class GameOverViewModel(
    private val repository: GameOverRepository
) {

    fun getScore(): String {
        val score = repository.currentScore()
        repository.clearScore()
        return score
    }
}
