package com.example.unscramblegame.presentation.game

import androidx.fragment.app.Fragment
import com.example.unscramblegame.presentation.Screen

object GameScreen : Screen.Replace() {

    override fun fragment(): Fragment {
        return GameFragment()
    }
}