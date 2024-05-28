package com.example.unscramblegame.game.presentation

import com.example.unscramblegame.game.data.GameRepository
import com.example.unscramblegame.main.presentation.MyViewModel

class GameViewModel(
    private val repository: GameRepository
) : MyViewModel {

    fun init(): GameUiState {
        repository.saveLastScreenIsGame()
        return GameUiState.Question(
            counter = repository.currentCounter(),
            word = repository.currentWord().reversed(),
            score = repository.currentScore()
        )
    }

    fun clearBeforeGameOver() {
        repository.reset()
    }

    fun submit(guess: String): GameUiState = if (repository.check(guess))
        skip(false)
    else
        GameUiState.Error

    fun skip(isSkipped: Boolean = true): GameUiState {
        if (isSkipped) repository.incrementSkips()
        return if (repository.isLast()) {
            GameUiState.GameOver
        } else {
            repository.next()
            init()
        }
    }

    fun checkUserInput(guess: String): GameUiState = if (repository.sameLength(guess))
        GameUiState.Match
    else
        GameUiState.InsufficientInput

}