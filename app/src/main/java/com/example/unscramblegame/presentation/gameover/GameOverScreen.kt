package com.example.unscramblegame.presentation.gameover

import androidx.fragment.app.Fragment
import com.example.unscramblegame.presentation.Screen

object GameOverScreen : Screen.Replace() {

    override fun fragment(): Fragment {
        return GameOverFragment()
    }
}