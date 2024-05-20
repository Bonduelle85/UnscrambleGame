package com.example.unscramblegame.load.presentation

import com.example.unscramblegame.load.data.LoadRepository
import com.example.unscramblegame.main.presentation.MyViewModel
import com.example.unscramblegame.main.presentation.RunAsync

class LoadViewModel(
    private val repository: LoadRepository,
    runAsync: RunAsync,
) : MyViewModel.Abstract(runAsync) {

    fun init(firstRun: Boolean, showUi: (LoadUiState) -> Unit) {
        if (firstRun) {
            repository.saveLastScreenIsLoad()
            showUi.invoke(LoadUiState.Progress)
            runAsync(repository::load) { loadResult ->
                val uiState = if (loadResult.isSuccessful())
                    LoadUiState.Success
                else
                    LoadUiState.Error(loadResult.message())
                showUi.invoke(uiState)
            }
        }
    }

    fun retry(showUi: (LoadUiState) -> Unit) = init(true, showUi)
}