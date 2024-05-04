package com.example.unscramblegame

import android.app.Application
import com.example.unscramblegame.data.DataSource
import com.example.unscramblegame.data.PermanentStorage
import com.example.unscramblegame.data.Repository
import com.example.unscramblegame.presentation.GameViewModel

class App : Application() {

    lateinit var viewModel: GameViewModel

    override fun onCreate() {
        super.onCreate()
        viewModel =
            GameViewModel(Repository.Base(PermanentStorage.Base(this), DataSource.Base(), max = 2))
    }
}