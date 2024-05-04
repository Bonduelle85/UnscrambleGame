package com.example.unscramblegame.presentation

import android.app.Activity
import android.app.AlertDialog
import android.os.Build
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.unscramblegame.App
import com.example.unscramblegame.R
import com.example.unscramblegame.databinding.ActivityMainBinding
import com.example.unscramblegame.databinding.GameOverBinding


class MainActivity : AppCompatActivity() {

    private lateinit var uiState: UiState
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = (application as App).viewModel

        val gameOverDialog: UpdateScore = GameOverDialog(this) {
            uiState = viewModel.playAgain()
            uiState.update(
                binding.counterTextView,
                binding.wordTextView,
                binding.scoreTextView,
                binding.submitButton,
                binding.customInput
            )
        }

        binding.submitButton.setOnClickListener {
            uiState = viewModel.submit(guess = binding.customInput.getText())
            uiState.update(
                binding.counterTextView,
                binding.wordTextView,
                binding.scoreTextView,
                binding.submitButton,
                binding.customInput
            )
            uiState.update(gameOverDialog)
        }

        binding.skipButton.setOnClickListener {
            uiState = viewModel.skip()
            uiState.update(
                binding.counterTextView,
                binding.wordTextView,
                binding.scoreTextView,
                binding.submitButton,
                binding.customInput
            )
            uiState.update(gameOverDialog)
        }

        uiState = if (savedInstanceState == null)
            viewModel.init()
        else {
            (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                savedInstanceState.getSerializable(KEY, UiState::class.java)
            else
                savedInstanceState.getSerializable(KEY)) as UiState
        }

        uiState.update(
            binding.counterTextView,
            binding.wordTextView,
            binding.scoreTextView,
            binding.submitButton,
            binding.customInput
        )
        uiState.update(gameOverDialog)
    }

    override fun onResume() {
        super.onResume()
        binding.customInput.addTextChangedListener {
            uiState = viewModel.checkUserInput(guess = it.toString())
            uiState.update(
                binding.counterTextView,
                binding.wordTextView,
                binding.scoreTextView,
                binding.submitButton,
                binding.customInput
            )
        }
    }

    override fun onPause() {
        super.onPause()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, uiState)
    }

    companion object {
        private const val KEY = "uiStateKey"
    }
}

private class GameOverDialog(
    private val activity: Activity,
    private val playAgain: () -> Unit
) : UpdateScore {

    private val builder: AlertDialog.Builder
    private lateinit var dialog: AlertDialog
    private val scoreTextView: TextView

    init {
        val binding = GameOverBinding.inflate(activity.layoutInflater)
        scoreTextView = binding.gameOverScoreTextView
        binding.playAgainButton.setOnClickListener {
            dialog.dismiss()
            playAgain.invoke()
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
