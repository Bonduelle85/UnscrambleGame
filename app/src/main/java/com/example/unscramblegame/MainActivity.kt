package com.example.unscramblegame

import android.app.AlertDialog
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.example.unscramblegame.databinding.ActivityMainBinding
import com.example.unscramblegame.databinding.GameOverBinding


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = GameViewModel(Repository.Base(max = 10)) // max = 2 for ScenarioTest

        val gameOverDialog: UpdateScore = GameOverDialog(this, viewModel)

        binding.submitButton.setOnClickListener {
            val uiState = viewModel.submit(guess = binding.inputEditText.text.toString())
            uiState.update(binding)
            uiState.update(gameOverDialog)
        }

        binding.skipButton.setOnClickListener {
            val uiState = viewModel.skip()
            uiState.update(binding)
            uiState.update(gameOverDialog)
        }

        binding.inputEditText.addTextChangedListener {
            val uiState = viewModel.checkUserInput(guess = it.toString())
            uiState.update(binding)
        }

        val uiState = viewModel.init()
        uiState.update(binding)
    }
}

private class GameOverDialog(
    private val activity: MainActivity,
    private val viewModel: PlayAgain
) : UpdateScore {

    private val builder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    private val scoreTextView: TextView

    init {
        val binding = GameOverBinding.inflate(activity.layoutInflater)
        scoreTextView = binding.gameOverScoreTextView
        binding.playAgainButton.setOnClickListener {
            dialog.dismiss()
            val uiState = viewModel.playAgain()
            uiState.update(activity.binding)
        }
        binding.exitButton.setOnClickListener {
            activity.finish()
        }
        builder = AlertDialog.Builder(activity).setView(binding.root)

        dialog = builder.create()
    }

    override fun showGameOver(score: String) {
        val text = activity.getString(R.string.scored, score)
        dialog.show()
        scoreTextView.text = text
    }
}

interface UpdateScore {
    fun showGameOver(score: String)
}

interface PlayAgain {
    fun playAgain(): UiState
}
