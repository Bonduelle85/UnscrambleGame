package com.example.unscramblegame.presentation.congratulation

import com.example.unscramblegame.data.Repository

class CongratulationViewModel(
    private val repository: Repository
) {


    fun getScore(): String {
        val score = repository.currentScore()
        repository.reset()
        return score
    }
}
