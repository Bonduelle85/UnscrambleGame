package com.example.unscramblegame.core.presentation


interface UiState

interface UpdateUi<T : UiState> {
    fun updateUiState(uiState: T)
}

interface UpdateObserver<T : UiState> {
    fun updateObserver(observer: (T) -> Unit)

    fun clearObserver()
}

interface UiObservable<T : UiState> : UpdateObserver<T>, UpdateUi<T> {

    abstract class Abstract<T : UiState> : UiObservable<T> {

        private var showUi: ((T) -> Unit)? = null
        private var cache: T? = null

        override fun updateUiState(uiState: T) {
            if (showUi != null) {
                showUi!!.invoke(uiState)
            } else {
                cache = uiState
            }
        }

        override fun updateObserver(observer: (T) -> Unit) {
            showUi = observer
            if (cache != null) {
                showUi!!.invoke(cache!!)
                cache = null // занулять не нужно, если нет кастом вьюх с сохранением состояния
            }
        }

        override fun clearObserver() {
            showUi = null
        }
    }
}