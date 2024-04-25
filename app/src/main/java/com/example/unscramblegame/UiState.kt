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

        override fun update(binding: ActivityMainBinding) {
            TODO("Not yet implemented")
        }


    }

    data class GameOver(private val score: String) : UiState {

        override fun update(binding: ActivityMainBinding) {

        }

        override fun update(updateScore: UpdateScore) {

        }

    }

    object InsufficientInput : UiState {

        override fun update(binding: ActivityMainBinding) {
            TODO("Not yet implemented")
        }

    }

    object Match : UiState {
        override fun update(binding: ActivityMainBinding) {

        }
    }

    object Error : UiState {
        override fun update(binding: ActivityMainBinding) {

        }
    }


}