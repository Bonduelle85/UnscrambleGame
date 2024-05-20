package com.example.unscramblegame.game.di

import com.example.unscramblegame.core.data.StringCache
import com.example.unscramblegame.core.di.Core
import com.example.unscramblegame.core.di.Module
import com.example.unscramblegame.core.di.ProvideAbstract
import com.example.unscramblegame.core.di.ProvideViewModel
import com.example.unscramblegame.game.data.GameRepository
import com.example.unscramblegame.game.presentation.GameViewModel
import com.example.unscramblegame.load.data.CacheDataSource

class GameModule(private val core: Core) : Module<GameViewModel> {

    override fun viewModel(): GameViewModel = with(core) {
        return GameViewModel(
            GameRepository.Base(
                score = score,
                uiIndex = uiIndex,
                currentIndex = currentIndex,
                failed = failed,
                corrects = corrects,
                incorrects = incorrects,
                skips = skips,
                cachedWords = CacheDataSource.Base(
                    StringCache.Base(
                        "GAME_DATA",
                        sharedPreferences,
                        gson.toJson(emptyList<String>())
                    ),
                    gson
                ),
                max = 10,
                lastScreen = lastScreen
            )
        )
    }
}

class ProvideGameViewModel(
    core: Core,
    provideOther: ProvideViewModel
) : ProvideAbstract(core, provideOther, GameViewModel::class.java) {

    override fun module() = GameModule(core)
}