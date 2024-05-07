package com.example.unscramblegame.presentation.game

import com.example.unscramblegame.data.Repository

class GameViewModel(private val repository: Repository) : PlayAgain {

    override fun playAgain(): GameUiState {
        repository.reset()
        return init()
    }

    fun submit(guess: String): GameUiState = if (repository.check(guess))
        skip()
    else
        GameUiState.Error

    fun skip(): GameUiState = if (repository.isLast())
        GameUiState.GoToCongratulations(repository.currentScore())
    else {
        repository.next()
        init()
    }

    fun checkUserInput(guess: String): GameUiState = if (repository.sameLength(guess))
        GameUiState.Match
    else
        GameUiState.InsufficientInput

    fun init(): GameUiState = GameUiState.Question(
        counter = repository.currentCounter(),
        word = repository.currentWord().reversed(),
        score = repository.currentScore()
    )
}