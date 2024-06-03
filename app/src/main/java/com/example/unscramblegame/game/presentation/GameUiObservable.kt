package com.example.unscramblegame.game.presentation

import com.example.unscramblegame.core.presentation.UiObservable

interface GameUiObservable : UiObservable<GameUiState> {

    class Base : UiObservable.Abstract<GameUiState>(), GameUiObservable
}