package com.example.unscramblegame.load.di

import com.example.unscramblegame.core.di.Core
import com.example.unscramblegame.core.di.Module
import com.example.unscramblegame.core.di.ProvideAbstract
import com.example.unscramblegame.core.di.ProvideViewModel
import com.example.unscramblegame.load.data.LoadRepository
import com.example.unscramblegame.load.presentation.LoadViewModel
import com.example.unscramblegame.main.presentation.RunAsync

class LoadModule(
    private val core: Core
) : Module<LoadViewModel> {

    override fun viewModel(): LoadViewModel {
        return LoadViewModel(
            LoadRepository.Base(),
            RunAsync.Base()
        )
    }
}

class ProvideLoadViewModel(
    core: Core,
    provideOther: ProvideViewModel
) : ProvideAbstract(core, provideOther, LoadViewModel::class.java) {

    override fun module() = LoadModule(core)
}