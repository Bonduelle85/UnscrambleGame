package com.example.unscramblegame.load.presentation

import androidx.fragment.app.Fragment
import com.example.unscramblegame.main.presentation.Screen

object LoadScreen : Screen.Replace() {
    override fun fragment(): Fragment {
        return LoadFragment()
    }
}