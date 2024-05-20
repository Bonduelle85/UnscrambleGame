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

    //    private val binding get() = _binding!!
    private val binding: FragmentLoadBinding
        get() = _binding ?: throw RuntimeException("FragmentLoadBinding == null")

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
        val viewModel = manageViewModels.viewModel(LoadViewModel::class.java)

//        val exit = {
//            manageViewModels.clear(LoadViewModel::class.java)
//            (activity as LoadNavigation).navigateFromLoad()
//        }

//        val showUi: (LoadUiState) -> Unit = { uiState ->
//            uiState.update(
//                progress = binding.progressBar,
//                error = binding.errorTextView,
//                retry = binding.retryButton
//            )
//            uiState.navigate(exit)
//        }

        binding.retryButton.setOnClickListener {
//            viewModel.retry(showUi)
            viewModel.retry()
        }

//        viewModel.init(savedInstanceState == null, showUi)
        viewModel.init(showUi = this)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

interface LoadNavigation {
    fun navigateFromLoad()
}
