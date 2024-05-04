package com.example.unscramblegame.presentation

import com.example.unscramblegame.R
import com.example.unscramblegame.views.input.InputAction
import com.example.unscramblegame.views.input.InputUiState
import com.example.unscramblegame.views.submit.UpdateSubmitButton
import com.example.unscramblegame.views.word.UpdateText
import java.io.Serializable

interface UiState : Serializable {

    fun update(
        counterView: UpdateText,
        wordView: UpdateText,
        scoreView: UpdateText,
        submitView: UpdateSubmitButton,
        inputView: InputAction
    )

    fun update(updateScore: UpdateScore) = Unit

    data class Question(
        private val counter: String,
        private val word: String,
        private val score: String
    ) : UiState {

        override fun update(
            counterView: UpdateText,
            wordView: UpdateText,
            scoreView: UpdateText,
            submitView: UpdateSubmitButton,
            inputView: InputAction
        ) {
            submitView.changeEnabled(false)
            counterView.update(counter)
            wordView.update(word)
            scoreView.update(score)
            inputView.updateState(InputUiState.Initial)
        }
    }

    data class GameOver(private val score: String) : UiState {

        override fun update(
            counterView: UpdateText,
            wordView: UpdateText,
            scoreView: UpdateText,
            submitView: UpdateSubmitButton,
            inputView: InputAction
        ) {
            scoreView.update(score)
            inputView.updateState(InputUiState.ClearError)
        }

        override fun update(updateScore: UpdateScore) {
            updateScore.showGameOver(score)
        }
    }

    object InsufficientInput : UiState {

        override fun update(
            counterView: UpdateText,
            wordView: UpdateText,
            scoreView: UpdateText,
            submitView: UpdateSubmitButton,
            inputView: InputAction
        ) {
            submitView.changeEnabled(false)
            inputView.updateState(InputUiState.ClearError)
        }

    }

    object Match : UiState {

        override fun update(
            counterView: UpdateText,
            wordView: UpdateText,
            scoreView: UpdateText,
            submitView: UpdateSubmitButton,
            inputView: InputAction
        ) {
            submitView.changeEnabled(true)
            inputView.updateState(InputUiState.ClearError)
        }
    }

    object Error : UiState {

        override fun update(
            counterView: UpdateText,
            wordView: UpdateText,
            scoreView: UpdateText,
            submitView: UpdateSubmitButton,
            inputView: InputAction
        ) {
            submitView.changeEnabled(false)
            inputView.updateState(InputUiState.Error(R.string.error))
        }
    }
}