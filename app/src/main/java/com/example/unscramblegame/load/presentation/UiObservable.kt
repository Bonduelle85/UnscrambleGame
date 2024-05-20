package com.example.unscramblegame.load.presentation

interface UpdateUi {
    fun updateUiState(uiState: LoadUiState)
}

interface UpdateObserver {
    fun updateObserver(observer: (LoadUiState) -> Unit)

    fun clearObserver()
}

interface UiObservable : UpdateObserver, UpdateUi {

    class Base : UiObservable {

        private var showUi: ((LoadUiState) -> Unit)? = null
        private var cache: LoadUiState? = null

        override fun updateUiState(uiState: LoadUiState) {
            if (showUi != null) {
                showUi!!.invoke(uiState)
            } else {
                cache = uiState
            }
        }

        override fun updateObserver(observer: (LoadUiState) -> Unit) {
            showUi = observer
            if (cache != null) {
                showUi!!.invoke(cache!!)
                cache = null
            }
        }

        override fun clearObserver() {
            showUi = null
        }
    }
}