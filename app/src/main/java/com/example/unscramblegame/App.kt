package com.example.unscramblegame

import android.app.Application
import android.content.Context
import com.example.unscramblegame.data.DataSource
import com.example.unscramblegame.data.GameOverRepository
import com.example.unscramblegame.data.GameRepository
import com.example.unscramblegame.data.MainRepository
import com.example.unscramblegame.data.core.BooleanCache
import com.example.unscramblegame.data.core.IntCache
import com.example.unscramblegame.data.core.StringCache
import com.example.unscramblegame.presentation.game.GameScreen
import com.example.unscramblegame.presentation.game.GameViewModel
import com.example.unscramblegame.presentation.gameover.GameOverViewModel
import com.example.unscramblegame.presentation.gameover.Statistics
import com.example.unscramblegame.presentation.main.MainViewModel

class App : Application() {

    lateinit var mainViewModel: MainViewModel
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
        val lastScreen =
            StringCache.Base(LAST_SCREEN, sharedPreferences, GameScreen::class.java.canonicalName)

        mainViewModel = MainViewModel(
            MainRepository.Base(lastScreen)
        )

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
                    max = 2,
                    lastScreen
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
                    lastScreen,
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
        const val LAST_SCREEN = "LAST_SCREEN"
    }
}