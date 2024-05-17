package com.example.unscramblegame

import com.example.unscramblegame.load.presentation.LoadUiState
import org.junit.Assert.*
import org.junit.Test

class LoadViewModelTest {

    /**
     * 1) progress -> error
     * 2) retry
     * 3) progress -> success
     */
    @Test
    fun test() {
        val repository = FakeLoadRepository()
        val viewModel = LoadViewModel(repository = repository)
        val showUi = FakeShowUi()

        viewModel.init(firstRun = true, showUi = showUi)

        assertEquals(LoadUiState.Progress, showUi.uiStateList[0])
        assertEquals(LoadUiState.Error(message = "failed to fetch data"), showUi.uiStateList[1])

        viewModel.retry(showUi = showUi)

        assertEquals(LoadUiState.Progress, showUi.uiStateList[2])
        assertEquals(LoadUiState.Success, showUi.uiStateList[3])

        //change configuration (rotate screen)
        assertEquals(4, showUi.uiStateList.size)
        viewModel.init(firstRun = false, showUi = showUi)
        assertEquals(4, showUi.uiStateList.size)
    }
}

class FakeShowUi : (LoadUiState) -> Unit {

    val uiStateList = mutableListOf<LoadUiState>()

    override fun invoke(loadUiState: LoadUiState) {
        uiStateList.add(loadUiState)
    }
}

class FakeLoadRepository : LoadRepository {

    private var returnSuccess = false

    override fun load(): LoadResult {
        return if (returnSuccess)
            LoadResult.Success
        else {
            returnSuccess = true
            LoadResult.Error(message = "failed to fetch data")
        }
    }
}