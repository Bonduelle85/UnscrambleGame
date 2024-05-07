package com.example.unscramblegame

import android.app.Application
import com.example.unscramblegame.data.DataSource
import com.example.unscramblegame.data.PermanentStorage
import com.example.unscramblegame.data.Repository
import com.example.unscramblegame.presentation.congratulation.CongratulationViewModel
import com.example.unscramblegame.presentation.game.GameViewModel

class App : Application() {

    lateinit var gameViewModel: GameViewModel
    lateinit var congratulationViewModel: CongratulationViewModel

    override fun onCreate() {
        super.onCreate()

        gameViewModel =
            GameViewModel(Repository.Base(PermanentStorage.Base(this), DataSource.Base(), max = 10))

        congratulationViewModel =
            CongratulationViewModel(
                Repository.Base(
                    PermanentStorage.Base(this),
                    DataSource.Base(),
                    max = 10
                )
            )
    }
}