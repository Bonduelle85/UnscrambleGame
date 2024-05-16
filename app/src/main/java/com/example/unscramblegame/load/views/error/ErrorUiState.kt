package com.example.unscramblegame.load.views.error

import android.view.View.GONE
import android.view.View.VISIBLE
import java.io.Serializable

interface ErrorUiState : Serializable {

    fun show(updateError: UpdateError)

    object Hide : ErrorUiState {

        override fun show(updateError: UpdateError) {
            updateError.updateUi("", GONE)
        }
    }

    data class Show(private val message: String) : ErrorUiState {

        override fun show(updateError: UpdateError) {
            updateError.updateUi(message, VISIBLE)
        }
    }
}