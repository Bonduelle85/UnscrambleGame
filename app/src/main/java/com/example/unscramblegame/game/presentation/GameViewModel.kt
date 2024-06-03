package com.example.unscramblegame.game.presentation

import com.example.unscramblegame.game.data.GameRepository
import com.example.unscramblegame.main.presentation.MyViewModel
import com.example.unscramblegame.main.presentation.RunAsync

class GameViewModel(
    runAsync: RunAsync,
    private val repository: GameRepository,
    private val uiObservable: GameUiObservable,
) : MyViewModel.Abstract(runAsync) {

    fun init(isFirstTime: Boolean = true) {
        if (isFirstTime) {
            repository.saveLastScreenIsGame()
            runAsync({
                repository.updateCurrentWord()
                GameUiState.Question(
                    counter = repository.currentCounter(),
                    word = repository.currentWord().reversed(),
                    score = repository.currentScore()
                )
            }) {
                uiObservable.updateUiState(it)
            }
        }
    }

    fun clearBeforeGameOver() {
        repository.reset()
    }

    fun submit(guess: String) {
        if (repository.check(guess))
            skip(false)
        else
            uiObservable.updateUiState(GameUiState.Error)

    }


    fun skip(isSkipped: Boolean = true) {
        if (isSkipped) repository.incrementSkips()
        if (repository.isLast()) {
            uiObservable.updateUiState(GameUiState.GameOver)
        } else {
            repository.next()
            init()
        }
    }

    fun checkUserInput(guess: String) {
        uiObservable.updateUiState(
            if (repository.sameLength(guess))
                GameUiState.Match
            else
                GameUiState.InsufficientInput
        )
    }

    fun startGettingUpdates(observer: (GameUiState) -> Unit) {
        uiObservable.updateObserver(observer)
    }

    fun stopGettingUpdates() {
        uiObservable.clearObserver()
    }
}