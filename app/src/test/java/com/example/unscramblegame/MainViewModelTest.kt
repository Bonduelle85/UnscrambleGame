package com.example.unscramblegame

import com.example.unscramblegame.main.data.MainRepository
import com.example.unscramblegame.main.presentation.MainViewModel
import com.example.unscramblegame.main.presentation.Screen
import org.junit.Assert.assertEquals
import org.junit.Test

class MainViewModelTest {

    @Test
    fun test() {
        val viewModel = MainViewModel(FakeRepository())
        var actual = viewModel.init(true)
        assertEquals(FakeScreen, actual)

        actual = viewModel.init(false)
        assertEquals(Screen.Empty, actual)

    }
}

private class FakeRepository : MainRepository {

    override fun lastSavedScreen(): Screen {
        return FakeScreen
    }
}

private object FakeScreen : Screen