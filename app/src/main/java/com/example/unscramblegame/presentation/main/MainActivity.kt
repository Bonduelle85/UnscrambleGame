package com.example.unscramblegame.presentation.main


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.unscramblegame.R
import com.example.unscramblegame.databinding.ActivityMainBinding
import com.example.unscramblegame.presentation.game.GameFragment


class MainActivity : AppCompatActivity(), Navigation {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null)
            navigate(GameFragment())
    }

    override fun navigate(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
    }
}


interface Navigation {
    fun navigate(fragment: Fragment)
}
