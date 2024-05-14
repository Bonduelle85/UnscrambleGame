package com.example.unscramblegame.gameover.di

import com.example.unscramblegame.core.di.Core
import com.example.unscramblegame.core.di.Module
import com.example.unscramblegame.core.di.ProvideAbstract
import com.example.unscramblegame.core.di.ProvideViewModel
import com.example.unscramblegame.gameover.data.GameOverRepository
import com.example.unscramblegame.gameover.presentation.GameOverViewModel


class GameOverModule(private val core: Core) : Module<GameOverViewModel> {

    override fun viewModel(): GameOverViewModel = with(core) {
        return GameOverViewModel(
            statistics,
            GameOverRepository.Base(
                score,
                corrects,
                incorrects,
                skips,
                lastScreen,
            )
        )
    }
}

class ProvideGameOverViewModel(
    core: Core,
    provideOther: ProvideViewModel
) : ProvideAbstract(core, provideOther, GameOverViewModel::class.java) {

    override fun module() = GameOverModule(core)
}