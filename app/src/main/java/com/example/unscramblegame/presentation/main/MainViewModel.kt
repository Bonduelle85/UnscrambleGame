package com.example.unscramblegame.presentation.main

import com.example.unscramblegame.data.MainRepository
import com.example.unscramblegame.presentation.Screen

class MainViewModel(
    private val mainRepository: MainRepository
) {
    fun init(firstRun: Boolean): Screen {
        return if (firstRun)
            mainRepository.lastSavedScreen()
        else
            Screen.Empty
    }
}