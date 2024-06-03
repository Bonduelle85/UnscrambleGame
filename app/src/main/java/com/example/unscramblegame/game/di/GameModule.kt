package com.example.unscramblegame.game.di

import com.example.unscramblegame.core.di.Core
import com.example.unscramblegame.core.di.Module
import com.example.unscramblegame.core.di.ProvideAbstract
import com.example.unscramblegame.core.di.ProvideViewModel
import com.example.unscramblegame.game.data.GameRepository
import com.example.unscramblegame.game.presentation.GameUiObservable
import com.example.unscramblegame.game.presentation.GameViewModel
import com.example.unscramblegame.load.data.cache.CacheDataSource

class GameModule(private val core: Core) : Module<GameViewModel> {

    override fun viewModel(): GameViewModel = with(core) {
        return GameViewModel(
            runAsync = core.runAsync,
            repository = GameRepository.Base(
                max = core.max,
                score = score,
                uiIndex = uiIndex,
                currentIndex = currentIndex,
                failed = failed,
                corrects = corrects,
                incorrects = incorrects,
                skips = skips,
                cachedWords = CacheDataSource.Base(
                    cacheModule.database().dao()
                ),
                lastScreen = lastScreen
            ),
            uiObservable = GameUiObservable.Base()
        )
    }
}

class ProvideGameViewModel(
    core: Core,
    provideOther: ProvideViewModel
) : ProvideAbstract(core, provideOther, GameViewModel::class.java) {

    override fun module() = GameModule(core)
}