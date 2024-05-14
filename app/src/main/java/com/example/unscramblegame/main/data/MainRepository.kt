package com.example.unscramblegame.main.data

import com.example.unscramblegame.core.data.StringCache
import com.example.unscramblegame.main.presentation.Screen

interface MainRepository {

    fun lastSavedScreen(): Screen

    class Base(
        private val lastScreen: StringCache,
    ) : MainRepository {

        override fun lastSavedScreen(): Screen {
            val string = lastScreen.read()
            return Class.forName(string).getDeclaredConstructor().newInstance() as Screen
        }
    }
}