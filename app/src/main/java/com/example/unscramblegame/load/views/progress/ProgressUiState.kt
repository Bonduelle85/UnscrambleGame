package com.example.unscramblegame.load.views.progress

import android.view.View.GONE
import android.view.View.VISIBLE
import java.io.Serializable

interface ProgressUiState : Serializable {
    fun show(updateProgress: UpdateProgress)

    object Show : ProgressUiState {
        override fun show(updateProgress: UpdateProgress) {
            updateProgress.updateUi(VISIBLE)
        }
    }

    object Hide : ProgressUiState {
        override fun show(updateProgress: UpdateProgress) {
            updateProgress.updateUi(GONE)
        }
    }
}