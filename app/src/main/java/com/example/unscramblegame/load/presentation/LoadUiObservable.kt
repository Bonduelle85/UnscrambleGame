package com.example.unscramblegame.load.presentation

import com.example.unscramblegame.core.presentation.UiObservable


interface LoadUiObservable : UiObservable<LoadUiState> {

    class Base : UiObservable.Abstract<LoadUiState>(), LoadUiObservable
}
