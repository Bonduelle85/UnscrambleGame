package com.example.unscramblegame.main.presentation

import com.example.unscramblegame.main.data.MainRepository

class MainViewModel(
    private val mainRepository: MainRepository
) : MyViewModel {
    fun init(firstRun: Boolean): Screen {
        return if (firstRun)
            mainRepository.lastSavedScreen()
        else
            Screen.Empty
    }
}

interface MyViewModel