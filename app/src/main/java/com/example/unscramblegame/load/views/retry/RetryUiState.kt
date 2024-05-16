package com.example.unscramblegame.load.views.retry

import android.view.View.GONE
import android.view.View.VISIBLE
import java.io.Serializable

interface RetryUiState : Serializable {

    fun show(update: UpdateRetry)

    object Show : RetryUiState {

        override fun show(update: UpdateRetry) {
            update.updateUi(VISIBLE)
        }
    }

    object Hide : RetryUiState {

        override fun show(update: UpdateRetry) {
            update.updateUi(GONE)
        }
    }
}