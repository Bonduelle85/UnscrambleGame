package com.example.unscramblegame.presentation.gameover

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unscramblegame.App
import com.example.unscramblegame.R
import com.example.unscramblegame.databinding.FragmentCongratulationBinding
import com.example.unscramblegame.presentation.game.GameFragment
import com.example.unscramblegame.presentation.main.Navigation


class GameOverFragment : Fragment() {

    private var _binding: FragmentCongratulationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCongratulationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = (requireActivity().application as App).gameOverViewModel

        if (savedInstanceState == null) {
            val score = viewModel.getScore()
            binding.gameOverScoreTextView.text =
                getString(R.string.scored, score)
        }

        binding.playAgainButton.setOnClickListener {
            (requireActivity() as Navigation).navigate(GameFragment())
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
