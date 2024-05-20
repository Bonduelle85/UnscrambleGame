package com.example.unscramblegame.load.presentation

import com.example.unscramblegame.load.data.LoadRepository
import com.example.unscramblegame.main.presentation.MyViewModel
import com.example.unscramblegame.main.presentation.RunAsync

class LoadViewModel(
    private val uiObservable: UiObservable, // observer
    private val repository: LoadRepository,
    runAsync: RunAsync,
) : MyViewModel.Abstract(runAsync) {

    fun init(firstRun: Boolean) {
        if (firstRun) {
            repository.saveLastScreenIsLoad()
//            updateUiInner(LoadUiState.Progress)
            uiObservable.updateUiState(LoadUiState.Progress)
            runAsync(repository::load) { loadResult ->
                if (loadResult.isSuccessful())
//                    updateUiInner(LoadUiState.Success)
                    uiObservable.updateUiState(LoadUiState.Success)
                else
//                    updateUiInner(LoadUiState.Error(loadResult.message()))
                    uiObservable.updateUiState(LoadUiState.Error(loadResult.message()))
            }
        }
    }

    fun retry() = init(true)


    // realisation observer pattern

//    private var showUi: ((LoadUiState) -> Unit)? = null
//    private var cache: LoadUiState? = null

    private fun updateUiInner(uiState: LoadUiState) {
//        if (showUi != null) {
//            showUi!!.invoke(uiState)
//        } else {
//            cache = uiState
//        }
        uiObservable.updateUiState(uiState)
    }


    fun startGettingUpdates(showUi: (LoadUiState) -> Unit) {
//        this.showUi = showUi
//        if (cache != null) {
//            this.showUi!!.invoke(cache!!)
//            cache = null
//        }
        uiObservable.updateObserver(showUi)
    }

    fun stopGettingUpdates() {
//        showUi = null
        uiObservable.clearObserver()
    }
}