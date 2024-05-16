package com.example.unscramblegame.main.presentation


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.unscramblegame.R
import com.example.unscramblegame.core.di.ManageViewModels
import com.example.unscramblegame.databinding.ActivityMainBinding
import com.example.unscramblegame.game.presentation.GameNavigation
import com.example.unscramblegame.game.presentation.GameScreen
import com.example.unscramblegame.gameover.presentation.GameOverNavigation
import com.example.unscramblegame.gameover.presentation.GameOverScreen
import com.example.unscramblegame.load.presentation.LoadNavigation
import com.example.unscramblegame.load.presentation.LoadScreen


class MainActivity : AppCompatActivity(), Navigation, ManageViewModels {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = viewModel(MainViewModel::class.java)

        val lastScreen = viewModel.init(savedInstanceState == null)
        navigate(lastScreen)
    }

    override fun navigate(screen: Screen) {
        screen.show(R.id.container, supportFragmentManager)
    }

    override fun clear(clazz: Class<out MyViewModel>) {
        (application as ManageViewModels).clear(clazz)
    }

    override fun <T : MyViewModel> viewModel(clazz: Class<T>): T {
        return (application as ManageViewModels).viewModel(clazz)
    }
}


interface Navigation : LoadNavigation, GameNavigation, GameOverNavigation {
    fun navigate(screen: Screen)

    override fun navigateFromLoad() {
        navigate(GameScreen)
    }

    override fun navigateFromGameScreen() {
        navigate(GameOverScreen)
    }

    override fun navigateFromGameOverScreen() {
        navigate(LoadScreen)
    }
}
