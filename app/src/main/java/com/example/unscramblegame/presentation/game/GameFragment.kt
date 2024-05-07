package com.example.unscramblegame.presentation.game

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unscramblegame.App
import com.example.unscramblegame.databinding.FragmentGameBinding
import com.example.unscramblegame.presentation.congratulation.CongratulationFragment
import com.example.unscramblegame.presentation.main.Navigation


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!
    private lateinit var gameViewModel: GameViewModel
    private lateinit var uiState: GameUiState

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameViewModel = (requireActivity().application as App).gameViewModel

        val showUi: () -> Unit = {
            uiState.update(
                counterView = binding.counterTextView,
                wordView = binding.wordTextView,
                scoreView = binding.scoreTextView,
                submitView = binding.submitButton,
                inputView = binding.customInput,
            )
        }

        binding.submitButton.setOnClickListener {
            uiState = gameViewModel.submit(guess = binding.customInput.getText())
            showUi.invoke()
            uiState.navigate {
                (requireActivity() as Navigation).navigate(CongratulationFragment())
            }
        }

        binding.skipButton.setOnClickListener {
            uiState = gameViewModel.skip()
            showUi.invoke()
            uiState.navigate {
                (requireActivity() as Navigation).navigate(CongratulationFragment())
            }
        }

        uiState = if (savedInstanceState == null) {
            gameViewModel.init()
        } else {
            (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                savedInstanceState.getSerializable(KEY, GameUiState::class.java)
            else
                savedInstanceState.getSerializable(KEY)) as GameUiState
        }
        showUi.invoke()
    }


    override fun onResume() {
        super.onResume()
        binding.customInput.addTextChangedListener {
            uiState = gameViewModel.checkUserInput(guess = it.toString())
            uiState.update(
                binding.counterTextView,
                binding.wordTextView,
                binding.scoreTextView,
                binding.submitButton,
                binding.customInput
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(KEY, uiState)
    }

    companion object {
        const val KEY = "key"
    }
}

interface PlayAgain {

    fun playAgain(): GameUiState
}