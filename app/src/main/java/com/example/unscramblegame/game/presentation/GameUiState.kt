package com.example.unscramblegame.game.presentation

import com.example.unscramblegame.R
import com.example.unscramblegame.core.views.input.InputAction
import com.example.unscramblegame.core.views.input.InputUiState
import com.example.unscramblegame.core.views.submit.UpdateSubmitButton
import com.example.unscramblegame.core.views.word.UpdateText
import java.io.Serializable

interface GameUiState : Serializable {

    fun update(
        counterView: UpdateText,
        wordView: UpdateText,
        scoreView: UpdateText,
        submitView: UpdateSubmitButton,
        inputView: InputAction
    ) = Unit

    fun navigate(showGameOver: () -> Unit) = Unit

    object GameOver : GameUiState {
        override fun navigate(showGameOver: () -> Unit) {
            showGameOver.invoke()
        }
    }

    data class Question(
        private val counter: String,
        private val word: String,
        private val score: String
    ) : GameUiState {

        override fun update(
            counterView: UpdateText,
            wordView: UpdateText,
            scoreView: UpdateText,
            submitView: UpdateSubmitButton,
            inputView: InputAction
        ) {
            submitView.changeEnabled(false)
            counterView.updateText(counter)
            wordView.updateText(word)
            scoreView.updateText(score)
            inputView.updateState(InputUiState.Initial)
        }
    }

    object InsufficientInput : GameUiState {

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

    object Match : GameUiState {

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

    object Error : GameUiState {

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