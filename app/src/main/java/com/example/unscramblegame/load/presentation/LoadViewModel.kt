package com.example.unscramblegame.load.presentation

import com.example.unscramblegame.load.data.LoadRepository
import com.example.unscramblegame.main.presentation.MyViewModel
import com.example.unscramblegame.main.presentation.RunAsync

class LoadViewModel(
    private val repository: LoadRepository,
    runAsync: RunAsync,
) : MyViewModel.Abstract(runAsync) {

    private var showUi: ((LoadUiState) -> Unit)? = null
    private var cache: LoadUiState? = null

    fun init(firstRun: Boolean) {
        if (firstRun) {
            repository.saveLastScreenIsLoad()
            updateUiInner(LoadUiState.Progress)
            runAsync(repository::load) { loadResult ->
                if (loadResult.isSuccessful())
                    updateUiInner(LoadUiState.Success)
                else
                    updateUiInner(LoadUiState.Error(loadResult.message()))
            }
        }
    }

    fun updateUiInner(uiState: LoadUiState) {
        if (showUi != null) {
            showUi!!.invoke(uiState)
        } else {
            cache = uiState
        }
    }

    fun retry() = init(true)

    fun stopUpdates() {
        showUi = null
    }

    fun startUpdates(loadFragment: (LoadUiState) -> Unit) {
        showUi = loadFragment
        if (cache != null) {
            showUi!!.invoke(cache!!)
            cache = null
        }
    }
}