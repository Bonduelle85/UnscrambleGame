package com.example.unscramblegame

import android.app.Application
import android.content.Context
import com.example.unscramblegame.data.DataSource
import com.example.unscramblegame.data.GameOverRepository
import com.example.unscramblegame.data.GameRepository
import com.example.unscramblegame.data.core.BooleanCache
import com.example.unscramblegame.data.core.IntCache
import com.example.unscramblegame.presentation.game.GameViewModel
import com.example.unscramblegame.presentation.gameover.GameOverViewModel

class App : Application() {

    lateinit var gameViewModel: GameViewModel
    lateinit var gameOverViewModel: GameOverViewModel

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences =
            getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        gameViewModel =
            GameViewModel(
                GameRepository.Base(
                    IntCache.Base(SCORE, sharedPreferences),
                    IntCache.Base(UI_INDEX, sharedPreferences, 1),
                    IntCache.Base(CURRENT_INDEX, sharedPreferences),
                    BooleanCache.Base(FAILED, sharedPreferences),
                    DataSource.Base(),
                    max = 2
                )
            )

        gameOverViewModel =
            GameOverViewModel(
                GameOverRepository.Base(
                    IntCache.Base(SCORE, sharedPreferences)
                )
            )
    }

    companion object {
        const val SCORE = "SCORE"
        const val UI_INDEX = "UI_INDEX"
        const val CURRENT_INDEX = "CURRENT_INDEX"
        const val FAILED = "FAILED"
    }
}