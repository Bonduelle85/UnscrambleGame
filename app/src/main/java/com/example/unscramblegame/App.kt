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
import com.example.unscramblegame.presentation.gameover.Statistics

class App : Application() {

    lateinit var gameViewModel: GameViewModel
    lateinit var gameOverViewModel: GameOverViewModel

    override fun onCreate() {
        super.onCreate()

        val sharedPreferences =
            getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)

        val score = IntCache.Base(SCORE, sharedPreferences)
        val uiIndex = IntCache.Base(UI_INDEX, sharedPreferences, 1)
        val currentIndex = IntCache.Base(CURRENT_INDEX, sharedPreferences)
        val failed = BooleanCache.Base(FAILED, sharedPreferences)
        val corrects = IntCache.Base(CORRECTS, sharedPreferences)
        val incorrects = IntCache.Base(INCORRECTS, sharedPreferences)
        val skips = IntCache.Base(SKIPS, sharedPreferences)


        gameViewModel =
            GameViewModel(
                GameRepository.Base(
                    score,
                    uiIndex,
                    currentIndex,
                    failed,
                    corrects,
                    incorrects,
                    skips,
                    DataSource.Base(),
                    max = 2
                )
            )

        gameOverViewModel =
            GameOverViewModel(
                Statistics.Base(this),
                GameOverRepository.Base(
                    score,
                    corrects,
                    incorrects,
                    skips,
                )
            )
    }

    companion object {
        const val SCORE = "SCORE"
        const val UI_INDEX = "UI_INDEX"
        const val CURRENT_INDEX = "CURRENT_INDEX"
        const val FAILED = "FAILED"
        const val CORRECTS = "CORRECTS"
        const val INCORRECTS = "INCORRECTS"
        const val SKIPS = "SKIPS"
    }
}