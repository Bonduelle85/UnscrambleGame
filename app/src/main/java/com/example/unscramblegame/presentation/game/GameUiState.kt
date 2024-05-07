package com.example.unscramblegame.presentation.game

import com.example.unscramblegame.R
import com.example.unscramblegame.views.input.InputAction
import com.example.unscramblegame.views.input.InputUiState
import com.example.unscramblegame.views.submit.UpdateSubmitButton
import com.example.unscramblegame.views.word.UpdateText
import java.io.Serializable

interface GameUiState : Serializable {

    fun update(
        counterView: UpdateText,
        wordView: UpdateText,
        scoreView: UpdateText,
        submitView: UpdateSubmitButton,
        inputView: InputAction
    )

    fun navigate(navigation: () -> Unit) = Unit

    data class GoToCongratulations(private val currentScore: String) : GameUiState {

        override fun navigate(navigation: () -> Unit) {
            navigation.invoke()
        }

        override fun update(
            counterView: UpdateText,
            wordView: UpdateText,
            scoreView: UpdateText,
            submitView: UpdateSubmitButton,
            inputView: InputAction
        ) = Unit
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
            counterView.update(counter)
            wordView.update(word)
            scoreView.update(score)
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