package com.example.unscramblegame.main.di

import com.example.unscramblegame.core.di.Core
import com.example.unscramblegame.core.di.Module
import com.example.unscramblegame.core.di.ProvideAbstract
import com.example.unscramblegame.core.di.ProvideViewModel
import com.example.unscramblegame.main.data.MainRepository
import com.example.unscramblegame.main.presentation.MainViewModel

class MainModule(private val core: Core) : Module<MainViewModel> {

    override fun viewModel(): MainViewModel {
        return MainViewModel(MainRepository.Base(core.lastScreen))
    }
}

class ProvideMainViewModel(
    core: Core,
    provideOther: ProvideViewModel
) : ProvideAbstract(core, provideOther, MainViewModel::class.java) {

    override fun module() = MainModule(core)
}