package com.example.unscramblegame.views.input

import java.io.Serializable

interface InputUiState : Serializable {

    fun update(inputAction: InputAction)

    object Initial : InputUiState {

        override fun update(inputAction: InputAction) = with(inputAction) {
            clearText()
            clearError()
            updateHasError(false)
        }
    }

    data class Error(private val value: Int) : InputUiState {

        override fun update(inputAction: InputAction) = with(inputAction) {
            updateError(value)
            updateHasError(true)
        }
    }

    object ClearError : InputUiState {

        override fun update(inputAction: InputAction) = with(inputAction) {
            updateHasError(false)
            clearError()
        }
    }
}