package com.example.unscramblegame.game.di

import com.example.unscramblegame.core.data.DataSource
import com.example.unscramblegame.core.di.Core
import com.example.unscramblegame.core.di.Module
import com.example.unscramblegame.core.di.ProvideAbstract
import com.example.unscramblegame.core.di.ProvideViewModel
import com.example.unscramblegame.game.data.GameRepository
import com.example.unscramblegame.game.presentation.GameViewModel

class GameModule(private val core: Core) : Module<GameViewModel> {

    override fun viewModel(): GameViewModel = with(core) {
        return GameViewModel(
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
    }
}

class ProvideGameViewModel(
    core: Core,
    provideOther: ProvideViewModel
) : ProvideAbstract(core, provideOther, GameViewModel::class.java) {

    override fun module() = GameModule(core)
}