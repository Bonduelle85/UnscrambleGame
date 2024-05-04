package com.example.unscramblegame.presentation

import com.example.unscramblegame.data.Repository

class GameViewModel(private val repository: Repository) : PlayAgain {

    override fun playAgain(): UiState {
        repository.reset()
        return init()
    }

    fun submit(guess: String): UiState = if (repository.check(guess))
        skip()
    else
        UiState.Error

    fun skip(): UiState = if (repository.isLast())
        UiState.GameOver(repository.currentScore())
    else {
        repository.next()
        init()
    }

    fun checkUserInput(guess: String): UiState = if (repository.sameLength(guess))
        UiState.Match
    else
        UiState.InsufficientInput

    fun init(): UiState = UiState.Question(
        counter = repository.currentCounter(),
        word = repository.currentWord().reversed(),
        score = repository.currentScore()
    )
}