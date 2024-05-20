package com.example.unscramblegame.load.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.unscramblegame.core.di.ManageViewModels
import com.example.unscramblegame.databinding.FragmentLoadBinding

class LoadFragment : Fragment(), (LoadUiState) -> Unit {

    private var _binding: FragmentLoadBinding? = null
    private val binding: FragmentLoadBinding
        get() = _binding ?: throw RuntimeException("FragmentLoadBinding == null")
    private lateinit var viewModel: LoadViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val manageViewModels = activity as ManageViewModels
        viewModel = manageViewModels.viewModel(LoadViewModel::class.java)

        binding.retryButton.setOnClickListener {
            viewModel.retry()
        }

        viewModel.init(firstRun = savedInstanceState == null)
    }

    override fun invoke(loadUiState: LoadUiState) {
        loadUiState.update(
            binding.progressBar,
            binding.errorTextView,
            binding.retryButton,
        )
        loadUiState.navigate {
            val manageViewModels = requireActivity() as ManageViewModels
            manageViewModels.clear(LoadViewModel::class.java)
            (requireActivity() as LoadNavigation).navigateFromLoad()
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopUpdates()
    }

    override fun onResume() {
        super.onResume()
        viewModel.startUpdates(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface LoadNavigation {
    fun navigateFromLoad()
}
