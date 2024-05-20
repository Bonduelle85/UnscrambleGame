package com.example.unscramblegame.load.presentation

import com.example.unscramblegame.load.data.LoadRepository
import com.example.unscramblegame.main.presentation.MyViewModel
import com.example.unscramblegame.main.presentation.RunAsync

class LoadViewModel(
    private val repository: LoadRepository,
    runAsync: RunAsync,
) : MyViewModel.Abstract(runAsync) {

    private var showUiInner: (LoadUiState) -> Unit = {}

    fun init(showUi: (LoadUiState) -> Unit) {
        showUiInner = showUi
    }

    fun init(firstRun: Boolean) {
        if (firstRun) {
            repository.saveLastScreenIsLoad()
            showUiInner.invoke(LoadUiState.Progress)
            runAsync(repository::load) { loadResult ->
                if (loadResult.isSuccessful())
                    showUiInner.invoke(LoadUiState.Success)
                else
                    showUiInner.invoke(LoadUiState.Error(loadResult.message()))
            }
        }
    }

    fun retry() = init(true)
}