package com.example.unscramblegame.core.di

import com.example.unscramblegame.main.presentation.MyViewModel

interface Module<T : MyViewModel> {

    fun viewModel(): T
}