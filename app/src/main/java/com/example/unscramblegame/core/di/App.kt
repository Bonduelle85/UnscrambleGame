package com.example.unscramblegame.core.di

import android.app.Application
import com.example.unscramblegame.main.presentation.MyViewModel

class App : Application(), ManageViewModels {

    private lateinit var factory: ManageViewModels

    override fun onCreate() {
        super.onCreate()

        factory = ProvideViewModel.Factory(ProvideViewModel.Make(Core(this)))
    }

    override fun clear(clazz: Class<out MyViewModel>) {
        factory.clear(clazz)
    }

    override fun <T : MyViewModel> viewModel(clazz: Class<T>): T {
        return factory.viewModel(clazz)
    }
}