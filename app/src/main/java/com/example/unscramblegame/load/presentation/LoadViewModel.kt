package com.example.unscramblegame.load.presentation

import com.example.unscramblegame.load.data.LoadRepository
import com.example.unscramblegame.main.presentation.MyViewModel
import com.example.unscramblegame.main.presentation.RunAsync

class LoadViewModel(
    private val uiObservable: LoadUiObservable,
    private val repository: LoadRepository,
    runAsync: RunAsync,
) : MyViewModel.Abstract(runAsync) {

    fun init(firstRun: Boolean) {
        if (firstRun) {
            repository.saveLastScreenIsLoad()
            uiObservable.updateUiState(LoadUiState.Progress)
            runAsync(repository::load) { loadResult ->
                if (loadResult.isSuccessful())
                    uiObservable.updateUiState(LoadUiState.Success)
                else
                    uiObservable.updateUiState(LoadUiState.Error(loadResult.message()))
            }
        }
    }

    fun retry() = init(true)

    fun startGettingUpdates(showUi: (LoadUiState) -> Unit) {
        uiObservable.updateObserver(showUi)
    }

    fun stopGettingUpdates() {
        uiObservable.clearObserver()
    }
}