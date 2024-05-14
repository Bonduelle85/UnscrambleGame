package com.example.unscramblegame.data

import com.example.unscramblegame.data.core.StringCache
import com.example.unscramblegame.presentation.Screen

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