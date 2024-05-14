package com.example.unscramblegame.core.di

import android.content.Context
import com.example.unscramblegame.R
import com.example.unscramblegame.core.data.BooleanCache
import com.example.unscramblegame.core.data.IntCache
import com.example.unscramblegame.core.data.StringCache
import com.example.unscramblegame.game.presentation.GameScreen
import com.example.unscramblegame.gameover.presentation.Statistics

class Core(context: Context) {

    val sharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)
    val statistics = Statistics.Base(context)
    val score = IntCache.Base(SCORE, sharedPreferences)
    val uiIndex = IntCache.Base(UI_INDEX, sharedPreferences, 1)
    val currentIndex = IntCache.Base(CURRENT_INDEX, sharedPreferences)
    val failed = BooleanCache.Base(FAILED, sharedPreferences)
    val corrects = IntCache.Base(CORRECTS, sharedPreferences)
    val incorrects = IntCache.Base(INCORRECTS, sharedPreferences)
    val skips = IntCache.Base(SKIPS, sharedPreferences)
    val lastScreen =
        StringCache.Base(LAST_SCREEN, sharedPreferences, GameScreen::class.java.canonicalName)


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