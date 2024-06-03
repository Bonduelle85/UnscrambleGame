package com.example.unscramblegame.ui

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.unscramblegame.main.presentation.MainActivity
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ScenarioTest {

    @get:Rule
    val scenarioRule = ActivityScenarioRule(MainActivity::class.java)

    private lateinit var gamePage: GamePage
    private lateinit var gameOverPage: GameOverPage

    @Before
    fun setup() {
        gamePage = GamePage(
            counter = "1/2",
            word = "animal".reversed(),
            score = "0"
        )
    }

    /**
     * Number 1 : Success Immediately
     *
     * Input correct word
     * submit button became enabled (state Match)
     * Press Submit
     * counter 1/2, word changed, input empty, submit disabled, score : 20 (state question)
     */
    @Test
    fun caseNumber1() {
        caseNumber10()

        gamePage.inputWord(text = "animal")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gamePage = GamePage(
            counter = "2/2",
            word = "auto".reversed(),
            score = "20"
        )
        gamePage.checkQuestionState()
        scenarioRule.scenario.recreate()
        gamePage.checkQuestionState()
    }

    /**
     * Number 2: Success after error
     *
     * Input incorrect word
     * submit button became enabled (State match)
     * Press Submit
     * Input has error , submit disabled (state Error)
     * Input correct word
     * submit button became enabled and input cleared (state match)
     * Press Submit
     * counter 1/10, word changed, input empty and no error, submit not enabled, score 10
     * (state Question)
     *
     */
    @Test
    fun caseNumber2() {
        caseNumber10()

        gamePage.inputWord(text = "aminal")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()

        gamePage.clickSubmit()
        gamePage.checkErrorState()
        scenarioRule.scenario.recreate()
        gamePage.checkErrorState()

        gamePage.inputWord(text = "animal")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gamePage = GamePage(
            counter = "2/2",
            word = "auto".reversed(),
            score = "10"
        )
        gamePage.checkQuestionState()
        scenarioRule.scenario.recreate()
        gamePage.checkQuestionState()
    }

    /**
     * Number 3: Skip
     *
     * Press Skip
     * word changed, counter 1/10, score 0 (State Question)
     */
    @Test
    fun caseNumber3() {
        caseNumber10()

        gamePage.clickSkip()
        gamePage = GamePage(
            counter = "2/2",
            word = "auto".reversed(),
            score = "0"
        )
        gamePage.checkQuestionState()
        scenarioRule.scenario.recreate()
        gamePage.checkQuestionState()
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
        caseNumber10()

        gamePage.inputWord(text = "animal")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gamePage = GamePage(
            counter = "2/2",
            word = "auto".reversed(),
            score = "20"
        )
        gamePage.checkQuestionState()
        scenarioRule.scenario.recreate()
        gamePage.checkQuestionState()

        gamePage.inputWord(text = "auot")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()

        gamePage.clickSubmit()
        gamePage.checkErrorState()
        scenarioRule.scenario.recreate()
        gamePage.checkErrorState()

        gamePage.inputWord(text = "auto")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gameOverPage = GameOverPage("30", "1", "1", "0")
        gameOverPage.check()
        scenarioRule.scenario.recreate()
        gameOverPage.check()

        gameOverPage.clickPlayAgain()

        gamePage = GamePage(
            counter = "1/2",
            word = "anecdote".reversed(),
            score = "0"
        )
        gamePage.checkQuestionState()
        scenarioRule.scenario.recreate()
        gamePage.checkQuestionState()
    }

    /**
     * Number 5
     *
     * Input less than required count of letters
     * nothing changed
     * Input correct count
     * submit enabled (state Match)
     * input more letters
     * submit disabled
     */
    @Test
    fun caseNumber5() {
        caseNumber10()

        gamePage.inputWord(text = "anim")
        gamePage.checkInsufficientState()
        scenarioRule.scenario.recreate()
        gamePage.checkInsufficientState()

        gamePage.inputWord(text = "animal")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()

        gamePage.inputWord(text = "animalwr")
        gamePage.checkInsufficientState()
        scenarioRule.scenario.recreate()
        gamePage.checkInsufficientState()
    }

    /**
     * Number 6: GameOver after submit and exit
     *
     * Run Testcase number 1
     * Run TestCase number 2
     * result: GameOver screen : score 30
     * Press exit
     * not visible to user anymore
     */
    @Test
    fun caseNumber6() {
        caseNumber10()

        gamePage.inputWord(text = "animal")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()


        gamePage.clickSubmit()

        gamePage = GamePage(
            counter = "2/2",
            word = "auto".reversed(),
            score = "20"
        )
        gamePage.checkQuestionState()
        scenarioRule.scenario.recreate()
        gamePage.checkQuestionState()

        gamePage.inputWord(text = "auot")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()

        gamePage.clickSubmit()
        gamePage.checkErrorState()
        scenarioRule.scenario.recreate()
        gamePage.checkErrorState()

        gamePage.inputWord(text = "auto")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gameOverPage = GameOverPage("30", "1", "1", "0")
        gameOverPage.check()
        scenarioRule.scenario.recreate()
        gameOverPage.check()


        gameOverPage.clickExit()
        assertTrue(scenarioRule.scenario.state != Lifecycle.State.RESUMED)
    }

    /**
     * Number 7: GameOver screen : score 20, correct 1, incorrect 1, skip 1
     *
     * Input correct word
     * submit button became enabled (state Match)
     * Press Submit
     * counter 1/2, word changed, input empty, submit disabled, score : 20 (state question)
     *
     * Input incorrect word
     * submit button became enabled (State match)
     * Press Submit
     * Input has error , submit disabled (state Error)
     *
     * Press Skip
     * result: GameOver screen : score 20, correct 1, incorrect 1, skip 1
     * Press exit
     * not visible to user anymore
     */
    @Test
    fun caseNumber7() {
        caseNumber10()

        gamePage.inputWord(text = "animal")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()


        gamePage.clickSubmit()

        gamePage = GamePage(
            counter = "2/2",
            word = "auto".reversed(),
            score = "20"
        )
        gamePage.checkQuestionState()
        scenarioRule.scenario.recreate()
        gamePage.checkQuestionState()

        gamePage.inputWord(text = "auot")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()

        gamePage.clickSubmit()
        gamePage.checkErrorState()
        scenarioRule.scenario.recreate()
        gamePage.checkErrorState()

        gamePage.clickSkip()
        gameOverPage = GameOverPage("20", "1", "1", "1")
        gameOverPage.check()
        scenarioRule.scenario.recreate()
        gameOverPage.check()
    }

    /**
     * Number 8: GameOver screen : score 20, correct 2, incorrect 2, skip 0
     *
     * Input incorrect word
     * submit button became enabled (state Match)
     * Press Submit
     * Input has error , submit disabled (state Error)
     *
     * Input correct word
     * submit button became enabled (state Match)
     * Press Submit
     * counter 1/2, word changed, input empty, submit disabled, score : 10 (state question)
     *
     * Input incorrect word
     * submit button became enabled (State match)
     * Press Submit
     * Input has error , submit disabled (state Error)
     *
     * Input correct word
     * submit button became enabled (state Match)
     * Press Submit
     *
     * result: GameOver screen : score 20, correct 2, incorrect 2, skip 0
     */
    @Test
    fun caseNumber8() {
        caseNumber10()

        gamePage.inputWord(text = "animla")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gamePage.checkErrorState()
        scenarioRule.scenario.recreate()
        gamePage.checkErrorState()

        gamePage.inputWord(text = "animal")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gamePage = GamePage(
            counter = "2/2",
            word = "auto".reversed(),
            score = "10"
        )
        gamePage.checkQuestionState()
        scenarioRule.scenario.recreate()
        gamePage.checkQuestionState()

        gamePage.inputWord(text = "auot")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gamePage.checkErrorState()
        scenarioRule.scenario.recreate()
        gamePage.checkErrorState()

        gamePage.inputWord(text = "auto")
        gamePage.checkMatchState()
        scenarioRule.scenario.recreate()
        gamePage.checkMatchState()

        gamePage.clickSubmit()

        gameOverPage = GameOverPage("20", "2", "2", "0")
        gameOverPage.check()
        scenarioRule.scenario.recreate()
        gameOverPage.check()
    }

    /**
     * Number 9: Skip 2 times - GameOver screen : score 0, correct 0, incorrect 0, skip 2
     *
     * Press Skip
     * counter 1/2, word changed, input empty, submit disabled, score : 0 (state question)
     *
     * Press Skip
     *
     * result: GameOver screen : score 0, correct 0, incorrect 0, skip 2
     */
    @Test
    fun caseNumber9() {
        caseNumber10()

        gamePage.clickSkip()

        gamePage = GamePage(
            counter = "2/2",
            word = "auto".reversed(),
            score = "0"
        )

        gamePage.checkQuestionState()
        scenarioRule.scenario.recreate()
        gamePage.checkQuestionState()

        gamePage.clickSkip()

        gameOverPage = GameOverPage("0", "0", "0", "2")
        gameOverPage.check()
        scenarioRule.scenario.recreate()
        gameOverPage.check()
    }

    /**
     * TestCase 10 (LoadPage)
     * 1) progress -> error
     * 2) click retry
     * 3) progress -> success
     */
    @Test
    fun caseNumber10() {
        val loadPage = LoadPage()
        loadPage.checkProgressState()
        scenarioRule.scenario.recreate()
        loadPage.checkProgressState()

        loadPage.waitUntilError()
        loadPage.checkErrorState(message = "No internet connection")
        scenarioRule.scenario.recreate()
        loadPage.checkErrorState(message = "No internet connection")

        loadPage.clickRetry()
        loadPage.checkProgressState()
        scenarioRule.scenario.recreate()
        loadPage.checkProgressState()

        loadPage.waitUntilDisappear()
        gamePage.checkQuestionState()
        scenarioRule.scenario.recreate()
        gamePage.checkQuestionState()
    }
}