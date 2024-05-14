package com.example.unscramblegame.gameover.presentation

import androidx.fragment.app.Fragment
import com.example.unscramblegame.main.presentation.Screen

object GameOverScreen : Screen.Replace() {

    override fun fragment(): Fragment {
        return GameOverFragment()
    }
}