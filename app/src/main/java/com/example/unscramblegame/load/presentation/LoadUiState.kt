package com.example.unscramblegame.load.presentation

import com.example.unscramblegame.load.views.error.ErrorUiState
import com.example.unscramblegame.load.views.error.UpdateError
import com.example.unscramblegame.load.views.progress.ProgressUiState
import com.example.unscramblegame.load.views.progress.UpdateProgress
import com.example.unscramblegame.load.views.retry.RetryUiState
import com.example.unscramblegame.load.views.retry.UpdateRetry

interface LoadUiState {

    fun update(progress: UpdateProgress, error: UpdateError, retry: UpdateRetry)
    fun navigate(exit: () -> Unit)

    data class Error(
        private val message: String
    ) : LoadUiState {
        override fun update(progress: UpdateProgress, error: UpdateError, retry: UpdateRetry) {
            progress.updateUiState(ProgressUiState.Hide)
            error.updateUiState(ErrorUiState.Show(message))
            retry.updateUiState(RetryUiState.Show)
        }

        override fun navigate(exit: () -> Unit) = Unit

    }

    object Progress : LoadUiState {
        override fun update(progress: UpdateProgress, error: UpdateError, retry: UpdateRetry) {
            progress.updateUiState(ProgressUiState.Show)
            error.updateUiState(ErrorUiState.Hide)
            retry.updateUiState(RetryUiState.Hide)
        }

        override fun navigate(exit: () -> Unit) = Unit

    }

    object Success : LoadUiState {
        override fun update(progress: UpdateProgress, error: UpdateError, retry: UpdateRetry) = Unit

        override fun navigate(exit: () -> Unit) {
            exit.invoke()
        }

    }
}