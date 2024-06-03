package com.example.unscramblegame.game.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unscramblegame.core.di.ManageViewModels
import com.example.unscramblegame.databinding.FragmentGameBinding


class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: GameViewModel

    private lateinit var showUi: (GameUiState) -> Unit

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

        val manageViewModels = activity as ManageViewModels
        viewModel = manageViewModels.viewModel(GameViewModel::class.java)

        val clearAndNavigate: () -> Unit = {
            viewModel.clearBeforeGameOver()
            manageViewModels.clear(GameViewModel::class.java)
            (requireActivity() as GameNavigation).navigateFromGameScreen()
        }

        showUi = { uiState ->
            uiState.update(
                counterView = binding.counterTextView,
                wordView = binding.wordTextView,
                scoreView = binding.scoreTextView,
                submitView = binding.submitButton,
                inputView = binding.customInput,
            )
            uiState.navigate(clearAndNavigate)
        }

        binding.submitButton.setOnClickListener {
            viewModel.submit(guess = binding.customInput.getText())
        }

        binding.skipButton.setOnClickListener {
            viewModel.skip()
        }


        viewModel.init(savedInstanceState == null)
    }

    override fun onResume() {
        super.onResume()
        viewModel.startGettingUpdates(observer = showUi)

        binding.customInput.addTextChangedListener {
            viewModel.checkUserInput(guess = it.toString())
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopGettingUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface GameNavigation {
    fun navigateFromGameScreen()
}
