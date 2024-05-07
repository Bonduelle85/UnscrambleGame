package com.example.unscramblegame.data

interface GameOverRepository {

    fun currentScore(): String
    fun clearScore()

    class Base(
        private val score: IntCache,
    ) : GameOverRepository {

        override fun currentScore(): String {
            return score.read().toString()
        }

        override fun clearScore() {
            score.save(0)
        }
    }
}
