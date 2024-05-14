package com.example.unscramblegame.gameover.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unscramblegame.core.di.ManageViewModels
import com.example.unscramblegame.databinding.FragmentGameoverBinding


class GameOverFragment : Fragment() {

    private var _binding: FragmentGameoverBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameoverBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manageViewModels = activity as ManageViewModels
        val viewModel = manageViewModels.viewModel(GameOverViewModel::class.java)

        if (savedInstanceState == null) {
            binding.gameOverScoreTextView.text = viewModel.stats()
        }

        binding.playAgainButton.setOnClickListener {
            viewModel.clear()
            (requireActivity() as GameOverNavigation).navigateFromGameOverScreen()
        }

        binding.exitButton.setOnClickListener {
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface GameOverNavigation {
    fun navigateFromGameOverScreen()
}
