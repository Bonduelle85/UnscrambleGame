package com.example.unscramblegame.load.di

import com.example.unscramblegame.core.data.StringCache
import com.example.unscramblegame.core.di.Core
import com.example.unscramblegame.core.di.Module
import com.example.unscramblegame.core.di.ProvideAbstract
import com.example.unscramblegame.core.di.ProvideViewModel
import com.example.unscramblegame.load.data.CacheDataSource
import com.example.unscramblegame.load.data.CloudDataSource
import com.example.unscramblegame.load.data.ListWrapper
import com.example.unscramblegame.load.data.LoadRepository
import com.example.unscramblegame.load.data.WordsService
import com.example.unscramblegame.load.presentation.LoadViewModel
import com.example.unscramblegame.main.presentation.RunAsync

class LoadModule(
    private val core: Core
) : Module<LoadViewModel> {

    override fun viewModel(): LoadViewModel = with(core) {
        return LoadViewModel(
            LoadRepository.Base(
                lastScreen,
                CloudDataSource.Base(
                    service = if (core.runUiTest) WordsService.Mock(gson) else WordsService.Base(),
                    gson
                ),
                CacheDataSource.Base(
                    StringCache.Base(
                        "GAME_DATA",
                        sharedPreferences,
                        gson.toJson(ListWrapper(emptyList()))
                    ),
                    gson
                )
            ),
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