package com.example.unscramblegame

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GameViewModelTest {

    private lateinit var viewModel: GameViewModel

    @Before
    fun setup() {
        viewModel = GameViewModel(repository = FakeRepository())
    }

    /**
        Number 1 : Success Immediately
        Input correct word
        submit button became enabled (state Match)
        Press Submit
        counter 1/10, word changed, input empty, submit disabled, score : 20 (state question)
     */
    @Test
    fun caseNumber1() {
        var actualUiState: UiState = viewModel.init()
        var expectedUiState: UiState = UiState.Question(
            counter = "1/2",
            word = "an".reversed(),
            score = "0"
        )
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "a")
        expectedUiState = UiState.InsufficientInput
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "an")
        expectedUiState = UiState.Match
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.submit(guess = "an")
        expectedUiState = UiState.Question(
            counter = "2/2",
            word = "EU".reversed(),
            score = "20"
        )
        assertEquals(expectedUiState, actualUiState)
    }

    /**
     * Number 2: Success after error
     *
     * Input incorrect word
     * submit button became enabled (State match)
     * Press Submit
     * Input has error , submit disabled(state Error)
     * Input correct word
     * submit button became enabled and input cleared (state match)
     * Press Submit
     * counter 1/10, word changed, input empty and no error, submit not enabled, score 10
     * (state Question)
     */
    @Test
    fun caseNumber2() {
        var actualUiState: UiState = viewModel.init()
        var expectedUiState: UiState = UiState.Question(
            counter = "1/2",
            word = "an".reversed(),
            score = "0"
        )
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "a")
        expectedUiState = UiState.InsufficientInput
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "aj")
        expectedUiState = UiState.Match
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.submit(guess = "aj")
        expectedUiState = UiState.Error
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "a")
        expectedUiState = UiState.InsufficientInput
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "an")
        expectedUiState = UiState.Match
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.submit(guess = "an")
        expectedUiState = UiState.Question(
            counter = "2/2",
            word = "EU".reversed(),
            score = "10"
        )
        assertEquals(expectedUiState, actualUiState)
    }

    /**
     * Number 3: Skip
     *
     * Press Skip
     * word changed, counter 1/10, score 0 (State Question)
     */
    @Test
    fun caseNumber3() {
        var actualUiState: UiState = viewModel.init()
        var expectedUiState: UiState = UiState.Question(
            counter = "1/2",
            word = "an".reversed(),
            score = "0"
        )
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.skip()
        expectedUiState = UiState.Question(
            counter = "2/2",
            word = "EU".reversed(),
            score = "0"
        )
        assertEquals(expectedUiState, actualUiState)
    }

    /**
     * Number 4: GameOver after submit and play again
     *
     * Run Testcase number 1
     * Run TestCase number 2
     * result: GameOver screen : score 30
     * Press play again
     * Word changed, score 0, counter 0/10, input empty
     */
    @Test
    fun caseNumber4() {
        var actualUiState: UiState = viewModel.init()
        var expectedUiState: UiState = UiState.Question(
            counter = "1/2",
            word = "an".reversed(),
            score = "0"
        )
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "a")
        expectedUiState = UiState.InsufficientInput
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "an")
        expectedUiState = UiState.Match
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.submit(guess = "an")
        expectedUiState = UiState.Question(
            counter = "2/2",
            word = "EU".reversed(),
            score = "20"
        )
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "e")
        expectedUiState = UiState.InsufficientInput
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "ei")
        expectedUiState = UiState.Match
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.submit(guess = "ei")
        expectedUiState = UiState.Error
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "e")
        expectedUiState = UiState.InsufficientInput
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "eu")
        expectedUiState = UiState.Match
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.submit(guess = "eu")
        expectedUiState = UiState.GameOver(score = "30")
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.playAgain()
        expectedUiState = UiState.Question(
            counter = "1/2",
            word = "EU".reversed(),
            score = "0"
        )
        assertEquals(expectedUiState, actualUiState)
    }

    /**
     * Number 5
     *
     * Input less  than required count of letters
     * nothing changed
     * Input correct count
     * submit enabled (state Match)
     * input more letters
     * submit disabled
     */
    @Test
    fun caseNumber5() {
        var actualUiState: UiState = viewModel.init()
        var expectedUiState: UiState = UiState.Question(
            counter = "1/2",
            word = "an".reversed(),
            score = "0"
        )
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "n")
        expectedUiState = UiState.InsufficientInput
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "na")
        expectedUiState = UiState.Match
        assertEquals(expectedUiState, actualUiState)

        actualUiState = viewModel.checkUserInput(guess = "nax")
        expectedUiState = UiState.InsufficientInput
        assertEquals(expectedUiState, actualUiState)
    }
}

private class FakeRepository : Repository {

    private val list = listOf("an", "EU", "XY")

    private var currentIndex = 0 // 0, 1, 2, 3, 4, 5, ... 1000
    private var uiIndex = 1 // 1, 2, 1, 2, 1, 2 // 1 .. max
    private var score: Int = 0
    private val max: Int = 2
    private var failed: Boolean = false

    override fun currentWord(): String {
        return list[currentIndex]
    }

    override fun currentScore(): String {
        return score.toString()
    }

    override fun currentCounter(): String {
        return "$uiIndex/$max"
    }

    override fun check(userInput: String): Boolean {
        return if (currentWord().equals(userInput, ignoreCase = true)) {
            score += if (failed) 10 else 20
            true
        } else {
            failed = true
            false
        }
    }

    override fun sameLength(userInput: String): Boolean {
        return userInput.length == currentWord().length
    }

    override fun isLast(): Boolean {
        return uiIndex == max
    }

    override fun next() {
        currentIndex++
        uiIndex++
        failed = false
    }

    override fun reset() {
        score = 0
        uiIndex = 1
        failed = false
    }
}