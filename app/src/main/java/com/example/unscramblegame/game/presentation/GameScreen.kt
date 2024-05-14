package com.example.unscramblegame.game.presentation

import androidx.fragment.app.Fragment
import com.example.unscramblegame.main.presentation.Screen

object GameScreen : Screen.Replace() {

    override fun fragment(): Fragment {
        return GameFragment()
    }
}