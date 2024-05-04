package com.example.unscramblegame

import com.example.unscramblegame.databinding.ActivityMainBinding

interface UiState {

    fun update(binding: ActivityMainBinding)
    fun update(updateScore: UpdateScore) = Unit

    data class Question(
        private val counter: String,
        private val word: String,
        private val score: String
    ) : UiState {

        override fun update(binding: ActivityMainBinding) = with(binding) {
            counterTextView.text = counter
            wordTextView.text = word
            scoreTextView.text = score
            inputEditText.setText("")
        }
    }

    data class GameOver(private val score: String) : UiState {

        override fun update(binding: ActivityMainBinding) {
            binding.scoreTextView.text = score
            binding.inputEditText.setText("")
        }
        override fun update(updateScore: UpdateScore) {
            updateScore.showGameOver(score)
        }
    }

    object InsufficientInput : UiState {

        override fun update(binding: ActivityMainBinding) = with(binding) {
            submitButton.isEnabled = false
            inputLayout.isErrorEnabled = false
            inputLayout.error = ""
        }
    }

    object Match : UiState {
        override fun update(binding: ActivityMainBinding) = with(binding) {
            submitButton.isEnabled = true
            inputLayout.isErrorEnabled = false
            inputLayout.error = ""
        }
    }

    object Error : UiState {
        override fun update(binding: ActivityMainBinding) = with(binding) {
            submitButton.isEnabled = false
            inputLayout.isErrorEnabled = true
            inputLayout.error = inputLayout.context.getString(R.string.error)
        }
    }
}