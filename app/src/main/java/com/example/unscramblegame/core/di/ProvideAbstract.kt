package com.example.unscramblegame.core.di

import com.example.unscramblegame.main.presentation.MyViewModel

abstract class ProvideAbstract(
    protected val core: Core,
    private val nextChain: ProvideViewModel,
    private val viewModelClass: Class<out MyViewModel>
) : ProvideViewModel {

    protected abstract fun module(): Module<out MyViewModel>

    override fun <T : MyViewModel> viewModel(clazz: Class<T>): T {
        return if (clazz == viewModelClass)
            module().viewModel() as T
        else
            nextChain.viewModel(clazz)
    }
}