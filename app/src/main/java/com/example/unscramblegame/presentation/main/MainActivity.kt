package com.example.unscramblegame.presentation.main


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unscramblegame.App
import com.example.unscramblegame.R
import com.example.unscramblegame.databinding.ActivityMainBinding
import com.example.unscramblegame.presentation.Screen
import com.example.unscramblegame.presentation.game.GameNavigation
import com.example.unscramblegame.presentation.game.GameScreen
import com.example.unscramblegame.presentation.gameover.GameOverNavigation
import com.example.unscramblegame.presentation.gameover.GameOverScreen


class MainActivity : AppCompatActivity(), Navigation {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = (application as App).mainViewModel

        val lastScreen = viewModel.init(savedInstanceState == null)
        navigate(lastScreen)
    }

    override fun navigate(screen: Screen) {
        screen.show(R.id.container, supportFragmentManager)
    }
}


interface Navigation : GameNavigation, GameOverNavigation {
    fun navigate(screen: Screen)

    override fun navigateFromGameScreen() {
        navigate(GameOverScreen)
    }

    override fun navigateFromGameOverScreen() {
        navigate(GameScreen)
    }
}
